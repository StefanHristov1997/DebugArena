package com.debugArena.service.impl;

import com.debugArena.exeption.ObjectNotFoundException;
import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.model.dto.view.ProblemDetailsInfoViewModel;
import com.debugArena.model.dto.view.ProblemShortInfoViewModel;
import com.debugArena.model.entity.LanguageEntity;
import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.LanguageEnum;
import com.debugArena.repository.LanguageRepository;
import com.debugArena.repository.ProblemRepository;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.ProblemService;
import com.debugArena.service.helpers.LoggedUserHelper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    private final LoggedUserHelper loggedUserHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, UserRepository userRepository, LanguageRepository languageRepository, LoggedUserHelper loggedUserHelper, ModelMapper modelMapper) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.loggedUserHelper = loggedUserHelper;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void addProblem(AddProblemBindingModel addProblemBindingModel) {

        ProblemEntity problemToSave = modelMapper.map(addProblemBindingModel, ProblemEntity.class);

        LanguageEntity language = languageRepository
                .findByName(addProblemBindingModel
                        .getLanguage())
                .orElseThrow(()->
                        new ObjectNotFoundException("Language " + addProblemBindingModel.getLanguage() + " is not found!"));

        UserEntity currentUser = loggedUserHelper.get();

        problemToSave.setAuthor(currentUser);
        problemToSave.setLanguage(language);
        problemRepository.save(problemToSave);

        currentUser.getAddedProblems().add(problemToSave);
        userRepository.save(currentUser);
    }

    @Override
    public List<ProblemShortInfoViewModel> getProblemsByLanguage(LanguageEnum language) {

        List<ProblemEntity> problemsByLanguageName = problemRepository.findProblemsByLanguageName(language);

        return problemsByLanguageName
                .stream()
                .map(problem ->
                        modelMapper.map(problem, ProblemShortInfoViewModel.class)).toList();
    }

    @Override
    public ProblemDetailsInfoViewModel getProblemDetails(Long id) {

        ProblemEntity problem = problemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Problem with " + id + "is not found!"));

        return modelMapper.map(problem, ProblemDetailsInfoViewModel.class);
    }

    @Override
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    @Override
    public List<DailyNotificationProblemViewModel> getDailyNotificationProblems() {

        List<ProblemEntity> problemsCreatedToday = problemRepository
                .findProblemsByCreatedOnIs(LocalDate.now());

        return problemsCreatedToday
                .stream()
                .map(problem ->
                        modelMapper.map(problem, DailyNotificationProblemViewModel.class)).toList();
    }

    @Override
    public void deleteProblemsCreatedLastYear() {

        List<ProblemEntity> problemsByCreatedOnLastYear = problemRepository
                .findProblemsByCreatedOnIsBefore(
                        LocalDate
                                .parse(LocalDate.now().getYear() + "-01" + "-01"));

        problemRepository.deleteAll(problemsByCreatedOnLastYear);
    }
}

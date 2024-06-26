package com.debugArena.service.impl;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.ArticleViewModel;
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

        //TODO: add no such language exception
        LanguageEntity language = languageRepository
                .findByName(addProblemBindingModel
                        .getLanguage()).orElseThrow();

        UserEntity currentUser = loggedUserHelper.get();

        problemToSave.setAuthor(currentUser);
        problemToSave.setLanguage(language);
        problemRepository.save(problemToSave);

        currentUser.getAddedProblems().add(problemToSave);
        userRepository.save(currentUser);
    }

    @Override
    public List<ArticleViewModel> getArticlesByLanguage(LanguageEnum language) {

        List<ProblemEntity> problemsByLanguageName = problemRepository.findProblemsByLanguageName(language);

        return problemsByLanguageName
                .stream()
                .map(problem ->
                        modelMapper.map(problem, ArticleViewModel.class)).toList();
    }
}

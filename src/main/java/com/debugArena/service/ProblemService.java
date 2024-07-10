package com.debugArena.service;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.DailyNotificationProblemViewModel;
import com.debugArena.model.dto.view.ProblemDetailsInfoViewModel;
import com.debugArena.model.dto.view.ProblemShortInfoViewModel;
import com.debugArena.model.enums.LanguageEnum;

import java.util.List;

public interface ProblemService {

    void addProblem(AddProblemBindingModel addProblemBindingModel);

    List<ProblemShortInfoViewModel> getArticlesByLanguage(LanguageEnum language);

    ProblemDetailsInfoViewModel getProblemDetails(Long id);

    List<DailyNotificationProblemViewModel> getDailyNotificationProblems();

    void deleteProblemById(Long id);

}

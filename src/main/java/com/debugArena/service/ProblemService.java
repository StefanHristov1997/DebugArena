package com.debugArena.service;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.ProblemShortInfoViewModel;
import com.debugArena.model.enums.LanguageEnum;

import java.util.List;

public interface ProblemService {

    void addProblem(AddProblemBindingModel addProblemBindingModel);

    List<ProblemShortInfoViewModel> getArticlesByLanguage(LanguageEnum language);

}

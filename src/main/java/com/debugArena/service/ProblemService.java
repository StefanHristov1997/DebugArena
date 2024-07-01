package com.debugArena.service;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import com.debugArena.model.dto.view.ArticleViewModel;
import com.debugArena.model.enums.LanguageEnum;

import java.util.List;

public interface ProblemService {

    void addProblem(AddProblemBindingModel addProblemBindingModel);

    List<ArticleViewModel> getArticlesByLanguage(LanguageEnum language);

}

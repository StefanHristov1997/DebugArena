package com.debugArena.service;

import com.debugArena.model.dto.binding.AddProblemBindingModel;
import org.springframework.beans.factory.parsing.Problem;

public interface ProblemService {

    void addProblem(AddProblemBindingModel addProblemBindingModel);
}

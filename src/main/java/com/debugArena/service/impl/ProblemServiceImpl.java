package com.debugArena.service.impl;

import com.debugArena.repository.ProblemRepository;
import com.debugArena.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }
}

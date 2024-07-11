package com.debugArena.schedulling;

import com.debugArena.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProblemCleanupScheduleTask {

    private final ProblemService problemService;

    @Autowired
    public ProblemCleanupScheduleTask(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void cleanupProblemsCreatedLastYear() {
        problemService.deleteProblemsCreatedLastYear();
    }
}

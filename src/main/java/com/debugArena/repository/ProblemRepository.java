package com.debugArena.repository;

import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.enums.LanguageEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {

    ProblemEntity findProblemById(Long id);

    List<ProblemEntity> findProblemsByLanguageName(LanguageEnum language);

    List<ProblemEntity> findProblemsByCreatedOnIs(LocalDate createdOn);

    List<ProblemEntity> findProblemsByCreatedOnIsBefore(LocalDate currentYear);

}

package com.debugArena.repository;

import com.debugArena.model.entity.ProblemEntity;
import com.debugArena.model.enums.LanguageEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {

    List<ProblemEntity> findProblemsByLanguageName(LanguageEnum language);
}

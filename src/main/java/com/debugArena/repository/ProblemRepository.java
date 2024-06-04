package com.debugArena.repository;

import com.debugArena.model.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
}

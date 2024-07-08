package com.debugArena.repository;

import com.debugArena.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByProblemIdOrderByRatingDesc(Long id);
    CommentEntity findCommentEntityById(Long id);
}

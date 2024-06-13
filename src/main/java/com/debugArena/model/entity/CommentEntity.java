package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(name = "is_aproved", nullable = false)
    private boolean isApproved;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "text_content", nullable = false)
    private String textContent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id")
    private ProblemEntity problem;
}

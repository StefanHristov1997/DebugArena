package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @Column(name = "text_content", nullable = false)
    private String textContent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id")
    private ProblemEntity problem;
}

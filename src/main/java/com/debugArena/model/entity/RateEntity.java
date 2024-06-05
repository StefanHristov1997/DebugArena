package com.debugArena.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class RateEntity extends BaseEntity {

    @ManyToOne
    private ProblemEntity problem;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private CommentEntity comment;

    private int rating;
}

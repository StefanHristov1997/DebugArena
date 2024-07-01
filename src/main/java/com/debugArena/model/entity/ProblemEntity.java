package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "problems")
public class ProblemEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @ManyToOne (optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

}

package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "problems")
public class ProblemEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @OneToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @OneToOne(optional = false)
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    @OneToOne(optional = false)
    @JoinColumn(name = "technology_id")
    private TechnologyEntity technology;

}

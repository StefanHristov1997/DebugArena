package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany (mappedBy = "problem", cascade = CascadeType.ALL)
    private Set<CommentEntity> comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private LanguageEntity language;

    public ProblemEntity() {
        this.comments = new HashSet<>();
    }

}

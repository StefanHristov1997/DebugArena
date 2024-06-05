package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.debugArena.model.entity.enums.LanguageEnum;

@Getter
@Setter
@Entity
@Table(name = "languages")
public class LanguageEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private LanguageEnum name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}

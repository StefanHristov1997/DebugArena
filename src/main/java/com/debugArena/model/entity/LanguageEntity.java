package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.debugArena.model.enums.LanguageEnum;

@Getter
@Setter
@Entity
@Table(name = "languages")
public class LanguageEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private LanguageEnum name;

    public LanguageEntity() {}

    public LanguageEntity(Long id, LanguageEnum languageEnum) {
        super();
        this.name = languageEnum;
    }
}

package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.debugArena.model.entity.enums.CategoryEnum;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CategoryEnum name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}

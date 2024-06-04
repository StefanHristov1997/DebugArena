package spring.project.debugarena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import spring.project.debugarena.model.entity.enums.CategoryEnum;

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

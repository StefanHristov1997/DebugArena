package spring.project.debugarena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "problems")
public class ProblemEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}

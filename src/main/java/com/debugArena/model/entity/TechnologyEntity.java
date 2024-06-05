package com.debugArena.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "technologies")
public class TechnologyEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

}

package com.debugArena.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String skills;

    @Column
    private String interests;

    @OneToMany(mappedBy = "author", targetEntity = ProblemEntity.class, fetch = FetchType.EAGER)
    private Set<ProblemEntity> addedProblems;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false))
    private Set<RoleEntity> roles;

    public UserEntity() {
        this.description = "";
        this.skills = "";
        this.interests = "";
        this.roles = new HashSet<>();
        this.addedProblems = new HashSet<>();
    }
}

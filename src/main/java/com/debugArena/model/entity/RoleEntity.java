package com.debugArena.model.entity;

import com.debugArena.model.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    public RoleEntity() {}

    public RoleEntity(Long id, UserRoleEnum userRoleEnum) {
        super();
        this.name = userRoleEnum;
    }
}

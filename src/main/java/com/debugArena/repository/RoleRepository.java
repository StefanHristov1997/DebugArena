package com.debugArena.repository;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Set<RoleEntity> findAllByNameIn(List<UserRoleEnum> roles);

}

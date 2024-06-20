package com.debugArena.init;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitRoles implements CommandLineRunner {

    private final Map<Long, UserRoleEnum> roles = Map.of(
           1L, UserRoleEnum.USER,
           2L, UserRoleEnum.ANONYMOUS,
           3L, UserRoleEnum.ADMIN,
           4L,  UserRoleEnum.MODERATOR
    );

    private final RoleRepository roleRepository;

    @Autowired
    public InitRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = roleRepository.count();

        if (count > 0) {
            return;
        }

        for (Long id : roles.keySet()) {
            RoleEntity role = new RoleEntity(id, roles.get(id));

            roleRepository.save(role);
        }
    }
}
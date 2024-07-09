package com.debugArena.service.impl;

import com.debugArena.model.entity.RoleEntity;
import com.debugArena.model.entity.UserEntity;
import com.debugArena.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(email)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " is not found"));
    }

    private UserDetails map(UserEntity user) {
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(mapAuthorities(user.getRoles()))
                .build();
    }

    private List<SimpleGrantedAuthority> mapAuthorities(Set<RoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .toList();
    }
}

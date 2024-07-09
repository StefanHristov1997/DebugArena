package com.debugArena.service.helpers;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserHelper {

    private final UserRepository userRepository;

    @Autowired
    public LoggedUserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity get() {
        String email = getEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " was not found"));
    }

    public boolean isLogged() {
        return !hasRole(UserRoleEnum.ANONYMOUS);
    }

    public boolean isAdmin() {
        return hasRole(UserRoleEnum.ADMIN);
    }

    public boolean isOnlyUser() {
        return getAuthentication().getAuthorities().stream()
                .allMatch(role -> role.getAuthority().equals("ROLE_" + UserRoleEnum.USER));
    }

    public String getEmail() {
        return getUserDetails().getUsername();
    }

    public boolean hasRole(UserRoleEnum userRoles) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_" + userRoles));
    }

    private UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

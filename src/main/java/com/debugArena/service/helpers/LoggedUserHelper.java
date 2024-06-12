package com.debugArena.service.helpers;

import com.debugArena.model.entity.UserEntity;
import com.debugArena.model.enums.UserRoleEnum;
import com.debugArena.repository.UserRepository;
import com.debugArena.service.impl.UserDetailsServiceImpl;
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
        String username = getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " was not found"));
    }

//    public boolean isLogged() {
//        return !hasRole(Use.ANONYMOUS);
//    }

    public boolean isAdmin() {
        return hasRole(UserRoleEnum.ADMIN);
    }

    public boolean isModerator() {
        return hasRole(UserRoleEnum.MODERATOR);
    }

    public boolean isOnlyUser() {
        return getAuthentication().getAuthorities().stream()
                .allMatch(role -> role.getAuthority().equals("ROLE_" + UserRoleEnum.USER));
    }

    public String getUsername() {
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

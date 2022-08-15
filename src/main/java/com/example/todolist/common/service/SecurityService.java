package com.example.todolist.common.service;

import com.example.todolist.common.data.NoAccessException;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.user.service.CustomUserDetailsService;
import com.example.todolist.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    private final CustomUserDetailsService customUserDetailsService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityService(CustomUserDetailsService customUserDetailsService, UserService userService, JwtUtils jwtUtils) {
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public void hasAccess(String rawToken, UUID userId) throws Exception {
        // z userId ziskat usera z databaze
        // z tokenu si vytahnout username (email)
        // user z databaze == emailu, ktery prisel v tokenu


        // validace tokenu tady neni potreba, protoze to dela ten jwtFilter

        var user = userService.findById(userId);
        var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        var token = rawToken.substring(7);
        var valid = jwtUtils.validateToken(token, userDetails);
        var tokenUsername = jwtUtils.getUsernameFromToken(token);
        var tokenUserDetails = customUserDetailsService.loadUserByUsername(tokenUsername);

        if (!valid && tokenUserDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            throw new NoAccessException("Invalid action");
        }
    }
}

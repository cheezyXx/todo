package com.example.todolist.auth.controllers;

import com.example.todolist.common.data.*;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.user.UserEntity;
import com.example.todolist.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authorization")
@RestController
@RequestMapping(path = "api/v1")
public class LoginController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> login(@RequestBody @Valid JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.email(),
                            jwtRequest.password()
                    )
            );
        } catch (InternalAuthenticationServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            e.getMessage(),
                            ErrorCodes.BAD_CREDENTIALS,
                            HttpStatus.UNAUTHORIZED.value()
                    ));
        }

        UserEntity userDetails = userService.findUserByEmail(jwtRequest.email());
        String token = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok().body(new JwtResponse(
                token,
                refreshToken,
                userService.getUserResponse(userDetails)));
    }

    @PostMapping("/token")
    public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        var userName = jwtUtils.getUsernameFromRefreshToken(refreshTokenRequest.refreshToken());
        UserEntity userDetails = userService.findUserByEmail(userName);
        var token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok().body(new RefreshTokenResponse(token));
    }
}

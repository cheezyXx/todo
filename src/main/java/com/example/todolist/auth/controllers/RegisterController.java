package com.example.todolist.auth.controllers;

import com.example.todolist.common.data.ErrorCodes;
import com.example.todolist.common.data.ErrorResponse;
import com.example.todolist.common.utils.JwtUtils;
import com.example.todolist.user.forms.RegisterForm;
import com.example.todolist.user.service.CustomUserDetailsService;
import com.example.todolist.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name="Authorization")
@RestController
@RequestMapping(path ="api/v1")
public class RegisterController {

    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

//    private final RegisterEmailService registerEmailService;

    private final JwtUtils jwtUtils;

    @Autowired
    public RegisterController(
            UserService userService,
            JwtUtils jwtUtils,
            CustomUserDetailsService customUserDetailsService
//            RegisterEmailService registerEmailService
    ) {

        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
//        this.registerEmailService = registerEmailService;
    }

    @PostMapping("/user")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterForm registerForm) throws Exception {
        try {
            var user = this.userService.saveUser(registerForm);
//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
//            String token = jwtUtils.generateToken(userDetails);

//            registerEmailService.sendRegisterEmail(user.getEmail(), token);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(
                    String.format("User [%s] already exists", registerForm.getEmail()),
                    ErrorCodes.USER_DUPLICATION,
                    HttpStatus.CONFLICT.value()
            ));
        }
    }

    @PostMapping("/user/{userId}/password")
    public ResponseEntity<Object> setPassword(@PathVariable("userId") UUID userId, @RequestParam("jwtToken") String token) {
        return ResponseEntity.ok().build();
    }
}

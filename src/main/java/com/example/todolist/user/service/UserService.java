package com.example.todolist.user.service;

import com.example.todolist.user.RoleEntity;
import com.example.todolist.user.UserEntity;
import com.example.todolist.user.forms.RegisterForm;
import com.example.todolist.user.reponse.UserResponse;
import com.example.todolist.user.repository.RoleRepository;
import com.example.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder
                       ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity findById(UUID id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("User not found [%s]", id)));
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(rollbackOn = Exception.class)
    public UserEntity saveUser(RegisterForm registerForm) throws Exception {
        try {
            UserEntity user = new UserEntity();

            user.setEmail(registerForm.getEmail());
            user.setActive(true);
            user.setFirstName(registerForm.getFirstName());
            user.setLastName(registerForm.getLastName());
            user.setPassword(passwordEncoder.encode(registerForm.getPassword()));

            RoleEntity userRole = roleRepository.findByRole("USER_BASIC");
            user.setRoles(new HashSet<RoleEntity>(Collections.singletonList(userRole)));
            return userRepository.save(user);
        } catch (Exception e) {
//            if (e instanceof ConstraintViolationException) {
//                throw new Error(String.format("User already exists %s", registerForm.getEmail()));
//            }
//
//            if (e instanceof IncorrectResultSizeDataAccessException) {
//                throw new Error(String.format("User already exists %s", registerForm.getEmail()));
//            }

            throw new Error(String.format("User was not created %s %s", registerForm.getEmail(), e.getClass()));
        }
    }

    public UserResponse getUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getId(),
                userEntity.getRoles()
        );
    }
}

package com.example.todolist;

import com.example.todolist.user.RoleEntity;
import com.example.todolist.user.repository.RoleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Tag(name="Default")
@SpringBootApplication
@RestController
public class TodolistApplication {

    private final RoleRepository roleRepository;

    @Autowired
    public TodolistApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @GetMapping
    public String hello() {
        return "Server is running";
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            try {
                this.roleRepository.saveAll(List.of(
                        new RoleEntity("ADMIN"),
                        new RoleEntity("USER_BASIC"),
                        new RoleEntity("USER_ADVANCED")
                ));
            } catch (Exception e) {

            }
        };
    }
}

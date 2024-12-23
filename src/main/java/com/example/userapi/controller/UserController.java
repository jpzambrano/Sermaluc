package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.service.UserService;
import com.example.userapi.validation.UserValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Endpoints para la gestión de usuarios")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userValidator.validate(user);

        userService.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new IllegalStateException("El correo ya esta registrado");
        });

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Lista los usuarios registrados")
     @GetMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados.");
        }

        List<User> activeUsers = users.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());

        return ResponseEntity.ok(activeUsers);
    }
}

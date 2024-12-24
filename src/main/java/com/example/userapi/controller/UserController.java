package com.example.userapi.controller;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.model.Phone;
import com.example.userapi.service.UserService;
import com.example.userapi.validation.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Registrar un usuario")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO request) {
        userValidator.validate(request);

        // Registrar usuario y generar respuesta usando streams y lambdas
        UserDTO savedUserDTO = userService.registerUser(
            userService.mapToEntity(request),
            request.getPhones().stream()
                    .map(phoneDTO -> new Phone(phoneDTO.getNumber(), phoneDTO.getCityCode(), phoneDTO.getCountryCode()))
                    .toList()
        );
    
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }


}

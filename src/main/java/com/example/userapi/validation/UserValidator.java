package com.example.userapi.validation;

import com.example.userapi.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Predicate;

@Component
public class UserValidator {

    private static final Map<Predicate<User>, String> validations = Map.of(
        u -> isEmailValid(u.getEmail()), "El formato del correo es inválido",
        u -> isPasswordValid(u.getPassword()), "El formato de la clave es inválido"
    );

    public void validate(User user) {
        validations.entrySet().stream()
            .filter(entry -> !entry.getKey().test(user))
            .findFirst()
            .ifPresent(entry -> {
                throw new IllegalArgumentException(entry.getValue());
            });
    }

    private static boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }

    private static boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return password != null && password.matches(passwordRegex);
    }
}

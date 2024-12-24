package com.example.userapi.validation;

import com.example.userapi.config.InvalidUserDataException;
import com.example.userapi.dto.UserDTO;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class UserValidator {

    public void validateUser(UserDTO userDTO) {
        validateField(userDTO.getName(), this::isValidName, "El nombre solo puede contener letras y espacios.");
        validateField(userDTO.getEmail(), this::isValidEmail, "El correo no tiene un formato válido.");
        validateField(userDTO.getPassword(), this::isValidPassword, "La contraseña debe cumplir con los requisitos de seguridad.");
        validatePhones(userDTO);
    }

    public void validatePhones(UserDTO userDTO) {
        if (userDTO.getPhones() == null || userDTO.getPhones().isEmpty()) {
            throw new InvalidUserDataException("Debe proporcionar al menos un número de teléfono.");
        }

        userDTO.getPhones().stream()
                .filter(phone -> phone.getNumber() == null || phone.getNumber().isBlank())
                .findFirst()
                .ifPresent(phone -> {
                    throw new InvalidUserDataException("El número de teléfono no puede estar vacío.");
                });

        userDTO.getPhones().stream()
                .filter(phone -> phone.getCityCode() == null || phone.getCityCode().isBlank())
                .findFirst()
                .ifPresent(phone -> {
                    throw new InvalidUserDataException("El código de ciudad no puede estar vacío.");
                });

        userDTO.getPhones().stream()
                .filter(phone -> phone.getCountryCode() == null || phone.getCountryCode().isBlank())
                .findFirst()
                .ifPresent(phone -> {
                    throw new InvalidUserDataException("El código de país no puede estar vacío.");
                });
    }

    private void validateField(String field, Predicate<String> validation, String errorMessage) {
        if (!validation.test(field)) {
            throw new InvalidUserDataException(errorMessage);
        }
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("^[a-zA-Z\\s]+$");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
    }
}

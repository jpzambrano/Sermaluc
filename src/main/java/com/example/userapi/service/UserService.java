package com.example.userapi.service;

import com.example.userapi.JwtKeyManager;
import com.example.userapi.dto.PhoneDTO;
import com.example.userapi.dto.UserDTO;
import com.example.userapi.model.Phone;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

      private final UserRepository userRepository;
    private final JwtKeyManager jwtKeyManager;

    public UserService(UserRepository userRepository, JwtKeyManager jwtKeyManager) {
        this.userRepository = userRepository;
        this.jwtKeyManager = jwtKeyManager;
    }

    public UserDTO registerUser(User user, List<Phone> phones) {
        // Asociar teléfonos al usuario
        phones.forEach(phone -> phone.setUser(user));
        user.setPhones(phones);

        // Configurar propiedades del usuario
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        // Generar y asignar token
        user.setToken(generateToken(user));

        // Guardar usuario en cascada
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    private String generateToken(User user) {
        SecretKey secretKey = jwtKeyManager.getSecretKey();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(user.getId().toString())
                .setIssuedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public UserDTO mapToDTO(User user) {
        List<PhoneDTO> phoneDTOs = user.getPhones().stream()
                .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                .toList();

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getToken(),
                user.getCreated().toString(),
                user.getModified().toString(),
                user.getLastLogin().toString(),
                user.getPassword(),
                phoneDTOs
        );
    }
    public User mapToEntity(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
    
    public List<Phone> mapToPhoneEntities(List<PhoneDTO> phoneDTOs) {
        return phoneDTOs.stream()
                .map(phoneDTO -> new Phone(
                        phoneDTO.getNumber(),
                        phoneDTO.getCityCode(),
                        phoneDTO.getCountryCode()
                ))
                .toList();
    }
}

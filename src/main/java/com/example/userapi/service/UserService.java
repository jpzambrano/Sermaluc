package com.example.userapi.service;

import com.example.userapi.JwtKeyManager;
import com.example.userapi.dto.PhoneDTO;
import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserResponseDTO;
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

    public UserService(UserRepository userRepository, JwtKeyManager jwtKeyManager) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO registerUser(UserDTO userDTO ,List<Phone> phones) {
        // Asociar teléfonos al usuario
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(userDTO.getPhones() != null ? userDTO.getPhones().stream()
                .map(phoneDTO -> Phone.builder()
                        .number(phoneDTO.getNumber())
                        .cityCode(phoneDTO.getCityCode())
                        .countryCode(phoneDTO.getCountryCode())
                        .build()) // user será asignado luego
                .toList() : null)
                .build();
        user.setToken(generateToken(user));
        if (user.getPhones() != null) {
            user.getPhones().forEach(phone -> phone.setUser(user));
        }

        // Guardar usuario en cascada
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    private String generateToken(User user) {
        SecretKey secretKey = JwtKeyManager.getSecretKey();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(user.getId().toString())
                .setIssuedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public UserResponseDTO mapToDTO(User user) {
        List<PhoneDTO> phoneDTOs = user.getPhones().stream()
                .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                .toList();

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created(user.getCreated().toString())
                .modified(user.getModified().toString())
                .lastLogin(user.getLastLogin().toString())
                .isActive(user.isActive())
                .token(user.getToken())
                .phones(phoneDTOs)
                .build();
    }
   
    public List<Phone> mapToPhoneEntities(List<PhoneDTO> phoneDTOs) {
        return phoneDTOs.stream()
                .map(phoneDTO -> Phone.builder()
                        .number(phoneDTO.getNumber())
                        .cityCode(phoneDTO.getCityCode())
                        .countryCode(phoneDTO.getCountryCode())
                        .build())
                .toList();
    }
}

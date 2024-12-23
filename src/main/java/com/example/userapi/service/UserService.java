package com.example.userapi.service;

import com.example.userapi.JwtKeyManager;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(generateToken(user));
        user.setActive(true);

        return userRepository.save(user);
    }

      public List<User> getAllUsers() {
        return userRepository.findAll();
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
}

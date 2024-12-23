package com.example.userapi.repository;

import com.example.userapi.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    List<Phone> findByUserId(UUID userId);
}
package com.example.userapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Data
public class Phone {
    @Id
    @GeneratedValue
    private UUID id;

    private String number;
    private String cityCode;
    private String countryCode;

    @Column(nullable = false)
    private UUID userId;
}
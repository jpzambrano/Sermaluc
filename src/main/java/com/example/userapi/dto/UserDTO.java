package com.example.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private boolean isActive;
    private String token;
    private String created;
    private String modified;
    private String lastLogin;
    private String password;
    private List<PhoneDTO> phones;
}

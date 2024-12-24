package com.example.userapi.dto;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class UserResponseDTO {
     private UUID id;
    private String name;
    private String email;
    private boolean isActive;
    private String token;
    private String created;
    private String modified;
    private String lastLogin;
    private List<PhoneDTO> phones;
}
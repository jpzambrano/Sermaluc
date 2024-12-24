
package com.example.userapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.userapi.controller.UserController;
import com.example.userapi.dto.PhoneDTO;
import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.service.UserService;
import com.example.userapi.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;


@WebMvcTest(UserController.class)
public class UserControllerTest {

   @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserValidator userValidator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_ShouldReturnCreatedStatus() throws Exception {
        // Datos de entrada simulados
        UserDTO request = new UserDTO();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPhones(List.of(
                new PhoneDTO("123456789", "1", "57"),
                new PhoneDTO("987654321", "2", "58")
        ));

        // Respuesta simulada del servicio
        UserResponseDTO response = new UserResponseDTO();
        response.setName("John Doe");
        response.setEmail("john.doe@example.com");
        response.setPhones(request.getPhones());

        // Configuración de mocks
        doNothing().when(userValidator).validatePhones(any(UserDTO.class));
        when(userService.registerUser(any(), any())).thenReturn(response);

        // Ejecutar el test
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phones[0].number").value("123456789"))
                .andExpect(jsonPath("$.phones[1].number").value("987654321"));
    }
}



package com.example.userapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void registerUser_ShouldReturnCreatedStatus() throws Exception {
        String newUserJson = "{ \"name\": \"John\", \"email\": \"john@example.com\", \"password\": \"Password@123\" }";

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void registerUser_WithInvalidEmail_ShouldReturnBadRequest() throws Exception {
        String invalidEmailJson = "{ \"name\": \"Jane\", \"email\": \"invalid-email\", \"password\": \"Password@123\" }";
    
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidEmailJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("El formato del correo es inválido"));
    }

    
                
}


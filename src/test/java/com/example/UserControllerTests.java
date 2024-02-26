package com.example;

import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.settings.Settings;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.example.settings.Settings.*;

@SpringBootTest(classes = ShopApplication.class)
public class UserControllerTests {
    
    
    private MockMvc mockMvc;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private ObjectMapper jackson;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    void addUser() throws Exception {
        final String OLEGUITO = "oleguito";
        CreateUserCommand userCommand = CreateUserCommand.builder()
                .login(OLEGUITO)
                .build();
                
        final String body = jackson.writeValueAsString(userCommand);
        mockMvc.perform(post(USERS_MAPPING + ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.login", equalTo(OLEGUITO))
        );
    }
}

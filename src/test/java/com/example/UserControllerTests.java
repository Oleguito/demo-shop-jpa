package com.example;

import com.example.presentation.user.UserController;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.example.testutil.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.example.settings.Settings.*;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@Rollback
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
    void listAllUsers() throws Exception {
        mockMvc.perform(get(USERS_MAPPING)).andExpectAll(
                status().isOk()
        );
    }

    
    @Test
    void getAUserById() throws Exception {
        final String OLEGUITO = "oleguito";
        CreateUserCommand userCommand = getCreateUserCommand(OLEGUITO);
        final String body = jackson.writeValueAsString(userCommand);
        ResultActions resultActions = TestUtils.postSomething(mockMvc, body, USERS_MAPPING + ADD);
        UserQuery userQuery = TestUtils.userQueryfromPostResult(resultActions, jackson);

        mockMvc.perform(get(USERS_MAPPING + "/" + userQuery.getId()))
            .andExpectAll(
            status().isOk(),
            jsonPath("$.login").exists(),
            jsonPath("$.login").value(OLEGUITO)
        );
    }
    
    @Test
    void addUser() throws Exception {
        final String OLEGUITO = "oleguito";
        CreateUserCommand userCommand = getCreateUserCommand(OLEGUITO);

        final String body = jackson.writeValueAsString(userCommand);
        // mockMvc.perform(post()
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content(body)
        // )
        TestUtils.postSomething(mockMvc, body, USERS_MAPPING + ADD)
                .andExpectAll(
                status().isOk(),
                jsonPath("$.login", equalTo(OLEGUITO))
        );
    }
    
    private static CreateUserCommand getCreateUserCommand(String login) {
        return CreateUserCommand.builder()
                .login(login)
                .build();
    }
    
    private final ResultActions postAUserByLogin(String login) throws Exception {
        CreateUserCommand userCommand = getCreateUserCommand(login);

        final String body = jackson.writeValueAsString(userCommand);
        return mockMvc.perform(post(USERS_MAPPING + ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );
    }
}

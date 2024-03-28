package com.example.testutil;

import com.example.domain.entity.ProductBin;
import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static com.example.infrastructure.settings.Settings.ADD;
import static com.example.infrastructure.settings.Settings.USERS_MAPPING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class TestUtils {

    private static final ObjectMapper jackson = new ObjectMapper();
    
    public static ResultActions postSomething(
            MockMvc mockMvc,
            String body,
            String path
    ) {
        try {
            return mockMvc.perform(post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            );
        } catch (Exception e) {
            throw new RuntimeException("Пися!!", e);
        }
    }
    
    public static UserQuery userQueryfromPostResult(ResultActions resultActions) {
        try {
            return jackson.readValue(
                    resultActions.andReturn().getResponse().getContentAsString(),
                    UserQuery.class
            );
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static User userFromPostResult(ResultActions resultActions,
                                          ObjectMapper jackson) {
        try {
            return jackson.readValue(
                    resultActions.andReturn().getResponse().getContentAsString(),
                    User.class
            );
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static CreateUserCommand getCreateUserCommand(String login) {
        var user = CreateUserCommand.builder()
                .login(login)
                .email("oleguito@example.com")
                .password("123")
                .build();
        return user;
    }
    
    public static User postAndReturnUser(String userName,
                                         MockMvc usersMockMvc,
                                         ObjectMapper jackson) {
        String body = null;
        try {
            body = jackson.writeValueAsString(
                    getCreateUserCommand("oleguito")
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        final var createdUserFromPostResult = userFromPostResult(
                postSomething(usersMockMvc, body, USERS_MAPPING + ADD),
                jackson
        );
        return createdUserFromPostResult;
    }
    
}

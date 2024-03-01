package com.example.testutil;

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

import static com.example.settings.Settings.ADD;
import static com.example.settings.Settings.USERS_MAPPING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtils {

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
    
    public static UserQuery userQueryfromPostResult(ResultActions resultActions, ObjectMapper jackson) {
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
        return CreateUserCommand.builder()
                .login(login)
                .build();
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
        return userFromPostResult(
                postSomething(usersMockMvc, body, USERS_MAPPING + ADD),
                jackson
        );
    }
    
    public static String getPathToAddProductToAUsersProductBin(Long userId) {
        
        return "/users/" + userId + "/product-bin/add";
    }
    
    public static String getPathToDeleteProductFromAUsersProductBin(Long userId, Long productId) {
        return "/users/" + userId + "/product-bin/delete/" + productId;
    }
}

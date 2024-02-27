package com.example.testutil;

import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    
    public static CreateUserCommand getCreateUserCommand(String login) {
        return CreateUserCommand.builder()
                .login(login)
                .build();
    }
}

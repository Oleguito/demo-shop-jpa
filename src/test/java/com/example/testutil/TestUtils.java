package com.example.testutil;

import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtils {

    private final static ObjectMapper jackson = new ObjectMapper();
    
    @NotNull
    public static <T> ResultActions postEntityWithMockMvc(
            MockMvc mockMvc,
            T entity,
            String path) {
        try {
            String body = jackson.writeValueAsString(entity);
            var result =
                    mockMvc.perform(post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            );
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static Object
    createEntityCommandByTitle(String title, Class<?> clazz)
            throws IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {
        Object object = clazz.getConstructor().newInstance();
        Method method = clazz.getMethod("setTitle", String.class);
        method.invoke(object, title);
        return object;
    }
    
    // public static CreateCategoryCommand
    // createCategoryCommandByTitle(String title) {
    //     CreateCategoryCommand categoryCommand =
    //             new CreateCategoryCommand();
    //     categoryCommand.setTitle(title);
    //     return categoryCommand;
    // }
}

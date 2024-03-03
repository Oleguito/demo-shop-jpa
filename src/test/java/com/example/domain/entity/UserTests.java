package com.example.domain.entity;

import com.example.ShopApplication;
import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ShopApplication.class)
public class UserTests {
    
    @Test
    void createAUser() throws Exception {
        User user = new User();
        assertTrue(user != null);
    }
    
    @Test
    void buildAUser() throws Exception {
        User user = User.builder().build();
        assertTrue(user != null);
    }

}

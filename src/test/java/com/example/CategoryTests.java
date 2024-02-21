package com.example;

import com.example.domain.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(classes = ShopApplication.class)
public class CategoryTests {
    
    public static final String FOODS_CATEGORY_TITLE = "foods";
    public static final Category FOODS = new Category(FOODS_CATEGORY_TITLE);
    
    @Test
    void createACategory() throws Exception {
        var category = FOODS;
        assertTrue("", category.getTitle().equals(FOODS_CATEGORY_TITLE));
    }
    
    @Test
    void buildACategory() throws Exception {
        var category = Category.builder().title(FOODS_CATEGORY_TITLE).build();
        assertTrue("", category.getTitle().equals(FOODS_CATEGORY_TITLE));
    }
}

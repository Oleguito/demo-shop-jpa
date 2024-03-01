package com.example.testutil;

import com.example.presentation.category.CategoryController;
import com.example.presentation.product.ProductController;
import com.example.presentation.user.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllControllers {

    @Autowired
    private UserController userController;
    
    @Autowired
    private ProductController productController;
    
    @Autowired
    private CategoryController categoryController;

    @Autowired
    private ObjectMapper jackson;

}

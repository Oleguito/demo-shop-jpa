package com.example.presentation.category;

import com.example.application.CategoryService;
import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CategoryCreateCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    
    @GetMapping
    public List <CategoryQuery> getAllCategories() {
        return categoryService.getListOfCategories().stream().map(
                category -> modelMapper.map(category, CategoryQuery.class)
        ).collect(Collectors.toList());
    }
    
    @PostMapping("/add")
    public CategoryQuery addCategory(@RequestBody CategoryCreateCommand command) {
        Category fromCommand = modelMapper.map(command, Category.class);
        Category category = categoryService.addCategory(fromCommand);
        return modelMapper.map(category, CategoryQuery.class);
    }
}

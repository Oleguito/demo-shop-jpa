package com.example.presentation.category;

import com.example.application.CategoryService;
import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.settings.Settings.*;

@RestController
@RequestMapping(CATEGORIES)
@AllArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    
    @GetMapping
    public List <CategoryQuery> getAllCategories() {
        return categoryService.getListOfCategories().stream().map(
                category -> modelMapper.map(
                        category, CategoryQuery.class)
        ).collect(Collectors.toList());
    }
    
    @GetMapping("/{categoryTitle}")
    public CategoryQuery getCategoryByName(@PathVariable String categoryTitle) {
        var category =  categoryService.findByTitle(categoryTitle);
        return category == null
                ? new CategoryQuery()
                : modelMapper.map(category,
                CategoryQuery.class);
    }
    
    @PostMapping("/add")
    public CategoryQuery addCategory(@RequestBody CreateCategoryCommand command) {
        Category fromCommand =
                modelMapper.map(command, Category.class);
        Category category =
                categoryService.addCategory(fromCommand);
        return modelMapper.map(category, CategoryQuery.class);
    }
    
    @PostMapping("/add-product/{categoryTitle}")
    public ProductQuery addProductToCategory(
            @PathVariable String categoryTitle,
            @RequestBody CreateProductCommand categoryCommand
    ) {
        return new ProductQuery();
    }
    
    @DeleteMapping("/delete/{categoryTitle}")
    public void deleteCategoryByTitle(@PathVariable String categoryTitle) {
        categoryService.removeCategory(
                categoryService.findByTitle(categoryTitle));
    }
    
}

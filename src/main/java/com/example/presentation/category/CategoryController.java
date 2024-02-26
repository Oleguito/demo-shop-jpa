package com.example.presentation.category;

import com.example.application.CategoryService;
import com.example.application.mappers.CategoryMapper;
import com.example.application.mappers.ProductMapper;
import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import com.example.presentation.product.ProductController;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import com.example.settings.Settings;
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
    private final ProductController productController;
    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    
    private final String CATEGORY_TITLE_VAR = "categoryTitle";
    
    @GetMapping
    public List <CategoryQuery> getAllCategories() {
        return categoryService.getListOfCategories().stream().map(
                category -> modelMapper.map(
                        category, CategoryQuery.class)
        ).collect(Collectors.toList());
    }
    
    @GetMapping("/{" + CATEGORY_TITLE_VAR + "}")
    public CategoryQuery getCategoryByName(@PathVariable String categoryTitle) {
        var category =  categoryService.findByTitle(categoryTitle);
        return category == null
                ? new CategoryQuery()
                : modelMapper.map(category,
                CategoryQuery.class);
    }
    
    @PostMapping(ADD_CATEGORY)
    public CategoryQuery addCategory(@RequestBody CreateCategoryCommand command) {
        Category fromCommand =
                modelMapper.map(command, Category.class);
        Category category =
                categoryService.addCategory(fromCommand);
        return modelMapper.map(category, CategoryQuery.class);
    }
    
    @PostMapping(ADD_PRODUCT)
    public ProductQuery addProductToCategory(
            @RequestBody CreateProductCommand productCommand
    ) {
        Category category = categoryService.findByTitle(
                productCommand.getCategory().getTitle());
        return productController.createProductREST(productCommand);
    }

    @DeleteMapping(DELETE_CATEGORY + "/{" + CATEGORY_TITLE_VAR + "}")
    public void deleteCategoryByTitle(@PathVariable String categoryTitle) {
        categoryService.removeCategory(
                categoryService.findByTitle(categoryTitle));
    }
    
}

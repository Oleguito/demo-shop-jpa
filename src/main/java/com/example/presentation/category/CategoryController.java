package com.example.presentation.category;

import com.example.application.services.category.CategoryService;
import com.example.application.services.category.mapper.CategoryMapper;
import com.example.application.services.product.mapper.ProductMapper;
import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.category.dto.commands.ModifyCategoryCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import com.example.presentation.product.ProductController;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.infrastructure.settings.Settings.*;

@RestController
@RequestMapping(value="/categories")
@AllArgsConstructor
@CrossOrigin
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
    
    @PutMapping
    @RequestMapping(value="/modify/{id}", method={RequestMethod.PUT})
    @CrossOrigin
    public CategoryQuery modifyCategoryById(@PathVariable Long id,
                                            @RequestBody ModifyCategoryCommand modifyCategoryCommand) {
        
        return categoryMapper.categoryToQuery(
                categoryService.modifyCategoryById(id, modifyCategoryCommand)
        );
    }
    
    // @RequestMapping(value=DELETE_CATEGORY + "/{" + CATEGORY_TITLE_VAR + "}", method={RequestMethod.DELETE})
    // public void deleteCategoryByTitle(@PathVariable String categoryTitle) {
    //     categoryService.removeCategory(
    //             categoryService.findByTitle(categoryTitle));
    // }
    
    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE})
    @CrossOrigin(methods = {RequestMethod.DELETE})
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.removeCategoryById(id);
    }
    
    @RequestMapping(value="/{categoryId}", method = {RequestMethod.GET})
    @CrossOrigin
    public CategoryQuery findCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return categoryMapper.categoryToQuery(category);
    }
    
    public CategoryMapper getMapper() {
        return categoryMapper;
    }
}

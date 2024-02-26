package com.example.presentation.product;

import com.example.application.ProductService;
import com.example.domain.entity.Product;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;
    
    @GetMapping("/all")
    public List <ProductQuery> getAllProductsREST() {
        return productService.findAll().stream().map(
                product ->
                modelMapper.map(product, ProductQuery.class)
        ).collect(Collectors.toList());
    }
    
    
    @PostMapping("/add")
    public ProductQuery createProductREST(@RequestBody CreateProductCommand productCommand) {
        Product productFromCommand =
                modelMapper.map(productCommand, Product.class);
        Product product = productService.create(productFromCommand);
        return modelMapper.map(product, ProductQuery.class);
    }
}









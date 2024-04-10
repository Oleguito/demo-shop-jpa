package com.example.presentation.product;

import com.example.application.services.product.ProductService;
import com.example.application.services.product.mapper.ProductMapper;
import com.example.domain.entity.Product;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.command.UpdateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    
    private ModelMapper modelMapper;
    
    private ProductMapper productMapper;
    
    @GetMapping("/all")
    @CrossOrigin
    public List <ProductQuery> getAllProductsREST() {
        return productService.findAll().stream().map(
                product ->
                modelMapper.map(product, ProductQuery.class)
        ).collect(Collectors.toList());
    }
    
    @RequestMapping(value="/add", method={RequestMethod.POST})
    @CrossOrigin
    public ProductQuery createProductREST(@RequestBody CreateProductCommand productCommand) {
        Product productFromCommand =
                modelMapper.map(productCommand, Product.class);
        Product product = productService.create(productFromCommand);
        
        return modelMapper.map(product, ProductQuery.class);
    }
    
    @RequestMapping(value="/delete/{id}", method={RequestMethod.DELETE})
    @CrossOrigin
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
    
    @RequestMapping(value="/update/{id}", method={RequestMethod.PUT})
    @CrossOrigin
    public ProductQuery modifyProductById(@PathVariable Long id, @RequestBody UpdateProductCommand updater) {
        
        Product product = productService.modifyById(id, updater);
        return productMapper.toQuery(product);
    }
    
    public ProductMapper getMapper() {
        return productMapper;
    }
}









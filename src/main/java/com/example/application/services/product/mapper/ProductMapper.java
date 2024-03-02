package com.example.application.services.product.mapper;

import com.example.domain.entity.Product;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    
    public Product fromCommand(CreateProductCommand command);
    
    ProductQuery toQuery(Product product);
    
    Product toProduct(ProductQuery query);
    
    CreateProductCommand toCommand(Product product);
}

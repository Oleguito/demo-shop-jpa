package com.example.application.mappers;

import com.example.domain.entity.Product;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.product.dto.query.ProductQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        suppressTimestampInGenerated = true,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProductMapper {
    
    public Product fromCommand(CreateProductCommand command);
    
    ProductQuery toQuery(Product product);
}

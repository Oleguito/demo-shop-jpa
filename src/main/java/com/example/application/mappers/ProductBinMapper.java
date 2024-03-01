package com.example.application.mappers;

import com.example.domain.entity.ProductBin;
import com.example.presentation.productbin.dto.quieries.ProductBinQuery;
import org.mapstruct.Mapper;

@Mapper
public interface ProductBinMapper {
    
    
    ProductBinQuery toQuery(ProductBin newBin);
}

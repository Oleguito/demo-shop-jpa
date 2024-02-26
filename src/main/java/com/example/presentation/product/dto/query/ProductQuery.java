package com.example.presentation.product.dto.query;

import com.example.domain.entity.Category;
import lombok.Data;

@Data
public class ProductQuery {

    private String title;
    
    private Category category;

}

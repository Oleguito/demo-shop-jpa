package com.example.presentation.product.dto.command;

import com.example.domain.entity.Category;
import lombok.*;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {

    String title;
    
    Category category;
}

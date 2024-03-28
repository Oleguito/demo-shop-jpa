package com.example.presentation.product.dto.command;

import com.example.domain.entity.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand {

    String title;
    
    Category category;
}

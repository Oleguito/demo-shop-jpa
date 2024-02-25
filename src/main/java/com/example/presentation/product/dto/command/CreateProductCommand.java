package com.example.presentation.product.dto.command;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Setter;

@Data
public class CreateProductCommand {
    
    @Setter
    String title;
    
}

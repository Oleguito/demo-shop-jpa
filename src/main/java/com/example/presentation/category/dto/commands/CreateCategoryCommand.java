package com.example.presentation.category.dto.commands;

import lombok.Data;
import lombok.Setter;

@Data
public class CreateCategoryCommand {
    
    @Setter
    String title;

}

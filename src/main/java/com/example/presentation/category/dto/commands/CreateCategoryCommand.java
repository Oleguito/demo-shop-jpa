package com.example.presentation.category.dto.commands;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryCommand {
    
    String title;

}

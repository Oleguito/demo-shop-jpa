package com.example.application.mappers;

import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.example.presentation.category.dto.queries.CategoryQuery;
import org.mapstruct.Mapper;

@Mapper(suppressTimestampInGenerated = true,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CategoryMapper {

    public Category createCommandToCategory(CreateCategoryCommand command);
    
    public CategoryQuery categoryToQuery(Category category);

}

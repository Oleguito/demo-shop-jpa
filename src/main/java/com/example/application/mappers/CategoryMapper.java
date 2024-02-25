package com.example.application.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        suppressTimestampInGenerated = true,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CategoryMapper {



}

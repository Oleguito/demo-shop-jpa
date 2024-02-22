package com.example.infrastructure.configuration;

import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap <CreateCategoryCommand, Category>() {
            protected void configure() {
                map().setTitle(source.getTitle());
            }
         });
        return modelMapper;
    }
    
    @Bean
    public ObjectMapper jackson() {return new ObjectMapper(); }
}

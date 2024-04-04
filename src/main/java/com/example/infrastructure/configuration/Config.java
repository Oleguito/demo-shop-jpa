package com.example.infrastructure.configuration;

import com.example.domain.entity.Category;
import com.example.presentation.category.dto.commands.CreateCategoryCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/*") // Разрешить CORS запросы к любым URL
    //             .allowedOrigins("http://localhost:3000") // URL вашего клиентского приложения
    //             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные методы
    //             .allowedHeaders("") // Разрешить все заголовки
    //             .allowCredentials(true) // Разрешить отправку cookies
    //             .maxAge(3600); // Максимальное время, на которое результат предварительного запроса может быть кэширован
    // }
    
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**").allowedMethods("*");
    // }
}

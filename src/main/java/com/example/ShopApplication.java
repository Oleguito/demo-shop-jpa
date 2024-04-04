package com.example;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
    
    @PostConstruct
    CommandLineRunner commandLineRunner() {
        return (args) -> {
            final var batchFileName = "C:\\Users\\1\\Desktop\\homework\\demo-shop-frontend\\populate-server-with-data.js";
            Runtime.getRuntime().exec("cmd /c start node" + batchFileName);
        };
    }
}

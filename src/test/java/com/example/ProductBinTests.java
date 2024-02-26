package com.example;

import com.example.domain.entity.ProductBin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductBinTests {
    
    @Test
    void createAProductBin() throws Exception {
        final var productBin = new ProductBin();
        assertTrue(productBin != null);
    }
    
    @Test
    void buildAProductBin() throws Exception {
        final var productBin = ProductBin.builder().build();
        assertTrue(productBin != null);
    }
    
}

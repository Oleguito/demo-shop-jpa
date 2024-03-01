package com.example.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.events.Event;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class ProductBin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @OneToMany
    List<Product> items = new ArrayList <>();

}

package com.example.domain.entity;

import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    List <Product> products;
}

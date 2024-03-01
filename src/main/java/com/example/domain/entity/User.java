package com.example.domain.entity;

import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String login;
    
    @OneToOne
    private final ProductBin productBin = new ProductBin();
    
    @OneToMany
    private List<Purchase> purchases;

}

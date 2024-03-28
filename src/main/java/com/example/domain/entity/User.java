package com.example.domain.entity;

import com.example.domain.enums.AccountType;
import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    
    @NotNull
    @NotEmpty
    String login;
    
    String password;
    
    String email;
    
    AccountType accountType;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private final ProductBin productBin = new ProductBin();
    
    @OneToMany
    private List<Purchase> purchases;

}

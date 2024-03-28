package com.example.presentation.user.dto.queries;

import com.example.domain.entity.ProductBin;
import com.example.domain.entity.Purchase;
import com.example.domain.enums.AccountType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuery {
    
    Long id;
    
    String login;
    
    String password;
    
    String email;
    
    AccountType accountType;
    
    private ProductBin productBin;
    
    private List <Purchase> purchases;
}

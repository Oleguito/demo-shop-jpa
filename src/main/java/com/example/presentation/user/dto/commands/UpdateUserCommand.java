package com.example.presentation.user.dto.commands;

import com.example.domain.entity.ProductBin;
import com.example.domain.entity.Purchase;
import com.example.domain.enums.AccountType;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
public class UpdateUserCommand {
    
    String login;
    
    String password;
    
    String email;
    
    AccountType accountType;
    
    ProductBin productBin;
    
    List <Purchase> purchases;
    
}

package com.example.presentation.user.dto.commands;

import com.example.domain.entity.ProductBin;
import com.example.domain.enums.AccountType;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
public class CreateUserCommand {
    
    String login;
 
    String password;
    
    String email;
    
    AccountType accountType;
}

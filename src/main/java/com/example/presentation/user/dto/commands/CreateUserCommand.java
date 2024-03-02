package com.example.presentation.user.dto.commands;

import com.example.domain.entity.ProductBin;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
public class CreateUserCommand {
    
    String login;
    
}

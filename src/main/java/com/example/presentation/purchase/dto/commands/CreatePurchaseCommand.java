package com.example.presentation.purchase.dto.commands;

import com.example.domain.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreatePurchaseCommand {
    
    User user;
}

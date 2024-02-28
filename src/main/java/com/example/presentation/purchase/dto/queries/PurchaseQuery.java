package com.example.presentation.purchase.dto.queries;

import com.example.domain.entity.User;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseQuery {
    
    User user;
}

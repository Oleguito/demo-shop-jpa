package com.example.presentation.user.dto.queries;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuery {
    
    Long id;
    
    String login;
    
}

package com.example.presentation.user.dto.queries;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserQuery {
    
    Long id;
    
    String login;
    
}

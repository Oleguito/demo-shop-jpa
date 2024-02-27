package com.example.presentation.user.dto.commands;

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
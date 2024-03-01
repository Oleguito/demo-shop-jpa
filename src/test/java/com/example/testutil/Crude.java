package com.example.testutil;

import com.example.presentation.user.dto.commands.CreateUserCommand;

public class Crude {
    
    public static CreateUserCommand createUserCommand(String userName) {
        return CreateUserCommand.builder().login(userName).build();
    }
}

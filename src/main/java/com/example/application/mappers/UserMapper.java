package com.example.application.mappers;

import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    
    User toUser(CreateUserCommand createUserCommand);
    
    UserQuery toUserQuery(User user);
}

package com.example.application.mappers;

import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring",
        suppressTimestampInGenerated = true,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {
    
    User toUser(UpdateUserCommand updateUserCommand);
    
    User toUser(CreateUserCommand createUserCommand);
    
    UserQuery toUserQuery(User user);
    
    List<UserQuery> toListUserQuery(List<User> users);
    
     User mapJsonToUser(String json);

}

package com.example.presentation.user;

import com.example.application.UserService;
import com.example.application.mappers.UserMapper;
import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.settings.Settings;

import static com.example.settings.Settings.*;

@RestController
@AllArgsConstructor
@RequestMapping(USERS_MAPPING)
public class UserController {
    
    private UserService userService;
    
    private UserMapper userMapper;
    
    @PostMapping("/add")
    public UserQuery postAUser(@RequestBody CreateUserCommand userCommand) {
        // User user = userMapper.toUser(userCommand);
        // user = userService.addUser(user);
        // UserQuery userQuery = userMapper.toUserQuery(user);
        UserQuery userQuery = new UserQuery();
        userQuery.setLogin("oleguito");
        return userQuery;
    }

}

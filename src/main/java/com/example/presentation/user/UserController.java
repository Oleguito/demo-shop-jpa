package com.example.presentation.user;

import com.example.application.UserService;
import com.example.application.mappers.UserMapper;
import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.settings.Settings;

import java.util.List;

import static com.example.settings.Settings.*;

@RestController
@AllArgsConstructor
@RequestMapping(USERS_MAPPING)
public class UserController {
    
    private UserService userService;
    
    private UserMapper userMapper;
    
    @GetMapping
    public List <UserQuery> getAllUsers() {
        return userMapper.toListUserQuery(userService.findAll());
    }
    
    @PostMapping("/add")
    public UserQuery postAUser(@RequestBody CreateUserCommand userCommand) {
        User user = userMapper.toUser(userCommand);
        user = userService.addUser(user);
        return userMapper.toUserQuery(user);
    }

}

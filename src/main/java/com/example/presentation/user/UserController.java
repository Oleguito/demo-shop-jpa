package com.example.presentation.user;

import com.example.application.UserService;
import com.example.application.mappers.UserMapper;
import com.example.domain.entity.User;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.settings.Settings;

import java.util.List;
import java.util.Optional;

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
    
    @GetMapping("/{id}")
    public UserQuery getUserById(@PathVariable Long id) {
        final var user = userService.getUserById(id);
        return user.isEmpty()
                ? new UserQuery()
                : userMapper.toUserQuery(user.get());
    }
    
    @PostMapping("/add")
    public UserQuery postAUser(@RequestBody CreateUserCommand userCommand) {
        User fromCommand = userMapper.toUser(userCommand);
        User user = userService.addUser(fromCommand);
        UserQuery userQuery = userMapper.toUserQuery(user);
        return userQuery;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAUser(@PathVariable Long id) {
        Optional <User> found = userService.getUserById(id);
        if(found.isEmpty()) return;
        User user = found.get();
        userService.deleteUser(user);
    }
    
    @PutMapping("/{id}")
    public UserQuery updateAUser(@PathVariable Long id, @RequestBody UpdateUserCommand updateUserCommand) {
        User updated = userService.updateUser(id,
                userMapper.toUser(updateUserCommand));
        return userMapper.toUserQuery(updated);
    }
}

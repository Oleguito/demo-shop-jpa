package com.example.presentation.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.example.application.services.user.UserService;
import com.example.application.mappers.ProductBinMapper;
import com.example.application.services.product.mapper.ProductMapper;
import com.example.application.services.user.mapper.UserMapper;
import com.example.domain.entity.Product;
import com.example.domain.entity.User;
import com.example.presentation.product.dto.command.CreateProductCommand;
import com.example.presentation.productbin.dto.quieries.ProductBinQuery;
import com.example.presentation.user.dto.commands.CreateUserCommand;
import com.example.presentation.user.dto.commands.UpdateUserCommand;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.infrastructure.settings.Settings.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = USERS_MAPPING)
@Slf4j
// @Validated
@CrossOrigin
public class UserController {
    
    private UserService userService;
    
    private UserMapper userMapper;
    
    private ProductMapper productMapper;
    
    private ProductBinMapper productBinMapper;
    
    @GetMapping
    public List <UserQuery> getAllUsers() {
        return userMapper.toListUserQuery(userService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserQuery> getUserById(@PathVariable Long id) {
        final var user = userService.getUserById(id);
        return user.isEmpty()
                ? ResponseEntity.ok(new UserQuery())
                : ResponseEntity.ok(userMapper.toUserQuery(user.get()));
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
    
    @GetMapping(path = "/{userId}" + PRODUCT_BIN,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductBinQuery> getProductBinOfA(
            @PathVariable Long userId
    ) {
        log.info("\n\nuserId: {}\n", userId);
        ProductBinQuery productBinQuery = new ProductBinQuery();
        return ResponseEntity.ok(productBinQuery);
    }
    
    @DeleteMapping("/{userId}/product-bin/delete/{productId}")
    public void deleteProductFromUsersProductBin(
            @PathVariable Long userId,
            @PathVariable Long productId) {
    
        userService.removeItemFromAProductBinForAUser(
                userId, productId
        );
    }
    
    @PostMapping("/{userId}" + PRODUCT_BIN + ADD)
    public ProductBinQuery putAnItemInAProductBin(
            @PathVariable Long userId,
            @RequestBody Product product
    ) {
        final var newBin
                = userService.putItemInAProductBinForUserWith(userId, product);
        ProductBinQuery query = productBinMapper.toQuery(newBin);
        return query;
    }
}

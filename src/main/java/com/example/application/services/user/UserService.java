package com.example.application.services.user;

import com.example.domain.entity.Product;
import com.example.domain.entity.ProductBin;
import com.example.domain.entity.User;
import com.example.infrastructure.repository.UserRepository;
import com.example.presentation.productbin.dto.quieries.ProductBinQuery;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User addUser(User user) {
        final var addedUser = userRepository.save(user);
        return addedUser;
    }
    
    public List <User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional <User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
    public User updateUser(Long id, User newUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) return new User();
        User user = optionalUser.get();
        for(var field : newUser.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getName().equals("id")) continue;
            trySet(newUser, field, user);
        }
        return userRepository.save(user);
    }
    
    private static void trySet(User newUser, Field field, User user) {
        try {
            field.set(user, field.get(newUser));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ProductBin putItemInAProductBinForUserWith(Long userId, Product product) {
        User foundUser = userRepository.findById(userId).orElseThrow();
        foundUser.getProductBin().getItems().add(product);
        userRepository.save(foundUser);
        return foundUser.getProductBin();
    }
    
    public void removeItemFromAProductBinForAUser(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        final var items = user.getProductBin().getItems();
        for (Product item : items) {
            if(item.getId().equals(productId)) {
                items.remove(item);
                break;
            }
        }
        userRepository.save(user);
    }
}

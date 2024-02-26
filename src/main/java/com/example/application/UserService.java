package com.example.application;

import com.example.domain.entity.User;
import com.example.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;
    
    public User addUser(User user) {
        return userRepository.save(user);
    }
}

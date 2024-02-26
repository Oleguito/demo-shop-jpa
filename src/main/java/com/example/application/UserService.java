package com.example.application;

import com.example.domain.entity.User;
import com.example.infrastructure.repository.UserRepository;
import com.example.presentation.user.dto.queries.UserQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private UserRepository userRepository;
    
    public User addUser(User user) {
        return userRepository.save(user);
    }
    
    public List <User> findAll() {
        return userRepository.findAll();
    }
}

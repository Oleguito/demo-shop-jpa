package com.example.application;

import com.example.domain.entity.User;
import com.example.infrastructure.repository.UserRepository;
import com.example.presentation.user.dto.queries.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User addUser(User user) {
        return userRepository.save(user);
    }
    
    public List <User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional <User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}

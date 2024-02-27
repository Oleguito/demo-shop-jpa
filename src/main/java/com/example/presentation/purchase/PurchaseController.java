package com.example.presentation.purchase;

import com.example.application.PurchaseService;
import com.example.application.UserService;
import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

    private PurchaseService purchaseService;
    
    private UserService userService;
    
    @GetMapping("/{id}")
    public List <Purchase> getAllPurchasesByUser(@PathVariable Long id) {
        
        User user = userService.getUserById(id)
                .orElseThrow(EntityNotFoundException::new);
        
        return new ArrayList<>();
    }

}

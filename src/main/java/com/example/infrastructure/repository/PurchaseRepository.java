package com.example.infrastructure.repository;

import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {
    
    //List <Purchase> getPurchasesByUser(User user);
    
}

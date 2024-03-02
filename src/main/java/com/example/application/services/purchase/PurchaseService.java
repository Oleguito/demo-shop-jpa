package com.example.application.services.purchase;

import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import com.example.infrastructure.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    
    // public List <Purchase> getPurchasesBy(User user) {
    //     return purchaseRepository.getPurchasesByUser(user);
    // }

}

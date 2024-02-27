package com.example.presentation.purchase;

import com.example.application.PurchaseService;
import com.example.domain.entity.Purchase;
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
    
    @GetMapping("/{id}")
    public List <Purchase> getAllPurchasesByUser(@PathVariable Long id) {
        return new ArrayList <>();
    }

}

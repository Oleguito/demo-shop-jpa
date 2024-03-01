package com.example.presentation.purchase;

import com.example.settings.Settings;
import com.example.application.PurchaseService;
import com.example.application.UserService;
import com.example.application.mappers.PurchaseMapper;
import com.example.domain.entity.Purchase;
import com.example.domain.entity.User;
import com.example.presentation.purchase.dto.commands.CreatePurchaseCommand;
import com.example.presentation.purchase.dto.queries.PurchaseQuery;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.settings.Settings.*;

@RestController
@AllArgsConstructor
@RequestMapping(PURCHASES_MAPPING)
public class PurchaseController {

    private PurchaseService purchaseService;
    
    private UserService userService;
    
    private PurchaseMapper purchaseMapper;
    
    @PostMapping(ADD)
    public PurchaseQuery addPurchase(
            @RequestBody CreatePurchaseCommand purchaseCommand) {
        
        final var fromCommand
                = purchaseMapper.toPurchase(purchaseCommand);
        
        return purchaseMapper.toQuery(fromCommand);
    }
    
    // @GetMapping("/{id}")
    // public List <PurchaseQuery> getAllPurchasesByUser(@PathVariable Long id) {
    //
    //     User user = userService.getUserById(id)
    //             .orElseThrow(EntityNotFoundException::new);
    //
    //     System.out.println("production found user: " + user);
    //
    //     return purchaseMapper.toListQueries(
    //             purchaseService.getPurchasesBy(user)
    //     );
    // }

}

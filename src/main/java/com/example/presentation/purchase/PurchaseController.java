package com.example.presentation.purchase;

import com.example.application.services.purchase.PurchaseService;
import com.example.application.services.user.UserService;
import com.example.application.services.purchase.mapper.PurchaseMapper;
import com.example.presentation.purchase.dto.commands.CreatePurchaseCommand;
import com.example.presentation.purchase.dto.queries.PurchaseQuery;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.infrastructure.settings.Settings.*;

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

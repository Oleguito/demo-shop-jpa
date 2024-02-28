package com.example.application.mappers;

import com.example.domain.entity.Purchase;
import com.example.presentation.purchase.dto.commands.CreatePurchaseCommand;
import com.example.presentation.purchase.dto.queries.PurchaseQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    
    Purchase toPurchase(CreatePurchaseCommand command);
    
    PurchaseQuery toQuery(Purchase fromCommand);
}

package com.example.testutil;

import static com.example.settings.Settings.*;

public class PathReturners {

    public static String getPathToListProductsInAUsersProductBin(Long userId) {
        return USERS_MAPPING + SLASH + userId + PRODUCT_BIN;
    }
    
    public static String getPathToAddProductToAUsersProductBin(Long userId) {
        
        return USERS_MAPPING + SLASH + userId + PRODUCT_BIN + ADD;
    }
    
    public static String getPathToDeleteProductFromAUsersProductBin(Long userId, Long productId) {
        
        return USERS_MAPPING + SLASH + userId + "/product-bin/delete/" + productId;
    }
}

package com.example.testutil;

public class PathReturners {

    public static String getPathToListProductsInAUsersProductBin(Long userId) {
        return "/users/" + userId + "/product-bin";
    }
    
    public static String getPathToAddProductToAUsersProductBin(Long userId) {
        
        return "/users/" + userId + "/product-bin/add";
    }
    
    public static String getPathToDeleteProductFromAUsersProductBin(Long userId, Long productId) {
        return "/users/" + userId + "/product-bin/delete/" + productId;
    }
}

package com.example.testutil;

public class PathReturners {

    public static String getPathToListProductsInAUsersProductBin(Long userId) {
        return "/users/" + userId + "/product-bin";
    }

}

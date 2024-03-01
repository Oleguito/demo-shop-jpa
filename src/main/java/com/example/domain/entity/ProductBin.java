package com.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductBin {

    List<Product> items = new ArrayList <>();

}

package com.example.presentation.productbin.dto.quieries;

import com.example.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class ProductBinQuery {

    List <Product> items = new ArrayList <>();

}

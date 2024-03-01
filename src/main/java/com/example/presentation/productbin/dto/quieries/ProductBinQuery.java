package com.example.presentation.productbin.dto.quieries;

import com.example.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBinQuery {

    List <Product> items = new ArrayList <>();

}

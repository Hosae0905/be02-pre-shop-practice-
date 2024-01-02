package com.example.onlineshop.order.model;

import com.example.onlineshop.product.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomDataDto {
    private String name;
    private String amount;
    private List<Product> customData;
}

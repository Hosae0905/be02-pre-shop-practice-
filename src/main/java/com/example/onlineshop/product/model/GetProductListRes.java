package com.example.onlineshop.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetProductListRes {
    private Long id;
    private String name;
    private Integer price;
}

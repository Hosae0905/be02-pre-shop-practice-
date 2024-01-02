package com.example.onlineshop.product.service;

import com.example.onlineshop.product.model.GetProductListRes;
import com.example.onlineshop.product.model.PostRegisterReq;
import com.example.onlineshop.product.model.Product;
import com.example.onlineshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<GetProductListRes> list() {
        List<Product> list = productRepository.findAll();
        List<GetProductListRes> products = new ArrayList<>();

        for (Product product : list) {
            products.add(GetProductListRes.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build());
        }

        return products;
    }

    public void register(PostRegisterReq postRegisterReq) {
        productRepository.save(Product.builder()
                .name(postRegisterReq.getName())
                .price(postRegisterReq.getPrice())
                .build());
    }
}

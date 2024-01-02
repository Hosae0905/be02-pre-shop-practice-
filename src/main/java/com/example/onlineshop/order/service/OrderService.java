package com.example.onlineshop.order.service;

import com.example.onlineshop.order.model.CustomDataDto;
import com.example.onlineshop.product.model.Product;
import com.example.onlineshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public Boolean calculate(String amount, CustomDataDto customDataDto) {

        Integer result = Math.round(Float.parseFloat(amount));
        System.out.println("result = " + result);
        Integer test = 0;

        for (Product product : customDataDto.getCustomData()) {
            Optional<Product> productInfo = productRepository.findById(product.getId());

            if (productInfo.isPresent()) {
                System.out.println(product.getPrice());
                test += product.getPrice();
            }
        }
        return result.equals(test);
    }
}

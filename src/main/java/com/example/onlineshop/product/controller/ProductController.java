package com.example.onlineshop.product.controller;

import com.example.onlineshop.product.model.PostRegisterReq;
import com.example.onlineshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<Object> list() {
        return ResponseEntity.ok().body(productService.list());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Object> register(@RequestBody PostRegisterReq postRegisterReq) {
        productService.register(postRegisterReq);
        return ResponseEntity.ok().body("ok");
    }
}

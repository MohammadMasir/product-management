package com.interview.product_management.controller;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    private final ProductService productService;

    @GetMapping("/add-product")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

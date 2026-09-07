package com.interview.product_management.service;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAll() {
        return null;
    }

    public ProductDto getById(String id) {
        return null;
    }

    public void addProduct(ProductDto productDto) {

    }

}

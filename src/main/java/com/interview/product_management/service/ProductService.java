package com.interview.product_management.service;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.enums.product.ProductStatus;
import com.interview.product_management.model.Product;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    public List<ProductDto> getAll() {
        return null;
    }

    public ProductDto getById(String id) {
        return null;
    }

    @Transactional
    public void add(ProductDto productDto) {

    }

    @Transactional
    public void buyProduct(Long id, int quantity) {
        Product product = productRepository.getReferenceById(id);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Transactional
    public void update(Long id, ProductDto productDto) {

    }

    @Transactional
    public void editProductStatus(Long id, ProductStatus productStatus) {

    }

}

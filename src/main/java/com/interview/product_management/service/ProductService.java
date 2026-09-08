package com.interview.product_management.service;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.enums.product.ProductStatus;
import com.interview.product_management.exceptions.ResourceNotFoundException;
import com.interview.product_management.model.Product;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getProductStatus()
                )).toList();
    }

    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getProductStatus()
        );
    }

    @Transactional
    public void add(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        product.setProductStatus(productDto.productStatus());
        productRepository.save(product);
    }

    @Transactional
    public void buyProduct(Long id, int quantity) {
        Product product = productRepository.getReferenceById(id);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Transactional
    public void update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        productRepository.save(product);
    }

    @Transactional
    public void editProductStatus(Long id, ProductStatus productStatus) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setProductStatus(productStatus);
        productRepository.save(product);
    }

}

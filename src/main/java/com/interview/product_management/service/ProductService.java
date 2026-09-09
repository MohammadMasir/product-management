package com.interview.product_management.service;

import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.enums.product.ProductStatus;
import com.interview.product_management.exceptions.InsufficientQuantityException;
import com.interview.product_management.exceptions.OutOfStockException;
import com.interview.product_management.exceptions.ResourceNotFoundException;
import com.interview.product_management.model.Product;
import com.interview.product_management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAllActiveProducts();
    }

    public Product getActiveProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getProductStatus().equals(ProductStatus.DISABLE)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No such Product!"
            );
        }
        return product;
    }

    public ProductDto getById(Long id) {
        Product product = getActiveProductById(id);
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity()
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
        Product product = getActiveProductById(id);
        if (product.getQuantity() < quantity && product.getQuantity() != 0) {
            throw new InsufficientQuantityException("Insufficient quantity, Only "+product.getQuantity()+" remaining.");
        }
        else if (product.getQuantity() == 0){
            throw new OutOfStockException("Product '"+product.getName()+"' is Out-of-Stock!");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Transactional
    public void update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.id()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
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

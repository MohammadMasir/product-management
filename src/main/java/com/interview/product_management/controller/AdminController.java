package com.interview.product_management.controller;

import com.interview.product_management.dto.auth.LoginDto;
import com.interview.product_management.dto.auth.RegisterDto;
import com.interview.product_management.dto.product.ProductDto;
import com.interview.product_management.enums.Role;
import com.interview.product_management.enums.product.ProductStatus;
import com.interview.product_management.service.AuthService;
import com.interview.product_management.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    private final ProductService productService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody RegisterDto registerDto) {
        authService.signup(registerDto, Role.ADMIN);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        authService.login(loginDto, request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/product/add")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.add(productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/product/{id}/editStatus")
    public ResponseEntity<Void> editProductStatus(@PathVariable Long id, ProductStatus productStatus) {
        productService.editProductStatus(id, productStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

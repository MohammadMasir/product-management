package com.interview.product_management.service;

import com.interview.product_management.dto.auth.LoginDto;
import com.interview.product_management.dto.auth.RegisterDto;
import com.interview.product_management.enums.Role;
import com.interview.product_management.model.Cart;
import com.interview.product_management.model.User;
import com.interview.product_management.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public void login(
            LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        securityContextRepository.saveContext(securityContext, request, response);
    }

    @Transactional
    public void signup(RegisterDto registerDto) {
        if (registerDto.passwordsMatch()) {
            User user = new User();
            user.setEmail(registerDto.email());
            user.setPassword(passwordEncoder.encode(registerDto.password()));
            user.setRole(Role.USER);
            Cart cart = new Cart();
            user.setCart(cart);
            cart.setUsers(user);
            userRepository.save(user);
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Passwords do not match!"
        );
    }

    @Transactional
    public void signup(RegisterDto registerDto, Role role) {
        if (registerDto.passwordsMatch()) {
            User user = new User();
            user.setEmail(registerDto.email());
            user.setPassword(passwordEncoder.encode(registerDto.password()));
            user.setRole(role);
            userRepository.save(user);
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Passwords do not match!"
        );
    }

}

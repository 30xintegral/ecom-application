package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOptional = productRepository.findById(request.getProductId());
        if(productOptional.isEmpty()){
            return false;
        }
        Product product = productOptional.get();
        if(product.getStock() < request.getQuantity()){
            return false;
        }
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return false;
        }
        User user = userOptional.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }
}

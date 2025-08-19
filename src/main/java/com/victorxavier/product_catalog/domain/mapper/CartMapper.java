package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.dto.CartDTO;
import com.victorxavier.product_catalog.domain.dto.CartItemDTO;
import com.victorxavier.product_catalog.domain.entity.Cart;
import com.victorxavier.product_catalog.domain.entity.CartItem;

import java.util.List;

public class CartMapper {
    
    public static CartDTO toDTO(Cart cart) {
        if (cart == null) {
            return null;
        }
        
        List<CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(CartMapper::toDTO)
                .toList();
        
        return new CartDTO(
                cart.getId(),
                cart.getUserId(),
                itemDTOs,
                cart.getTotalAmount(),
                cart.getTotalItems(),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }
    
    public static Cart toEntity(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        
        List<CartItem> items = cartDTO.items().stream()
                .map(CartMapper::toEntity)
                .toList();
        
        Cart cart = new Cart(cartDTO.userId());
        cart.setId(cartDTO.id());
        cart.setCreatedAt(cartDTO.createdAt());
        cart.setUpdatedAt(cartDTO.updatedAt());
        
        // Adiciona os itens ao carrinho
        items.forEach(item -> {
            CartItem cartItem = new CartItem(
                    item.getProductId(),
                    item.getProductName(),
                    item.getUnitPrice(),
                    item.getQuantity(),
                    item.getProductImageUrl(),
                    item.getAddedAt()
            );
            cart.addItem(cartItem);
        });
        
        return cart;
    }
    
    public static CartItemDTO toDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        
        return new CartItemDTO(
                cartItem.getProductId(),
                cartItem.getProductName(),
                cartItem.getUnitPrice(),
                cartItem.getQuantity(),
                cartItem.getSubtotal(),
                cartItem.getProductImageUrl(),
                cartItem.getAddedAt()
        );
    }
    
    public static CartItem toEntity(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        }
        
        CartItem cartItem = new CartItem(
                cartItemDTO.productId(),
                cartItemDTO.productName(),
                cartItemDTO.unitPrice(),
                cartItemDTO.quantity(),
                cartItemDTO.productImageUrl()
        );
        cartItem.setAddedAt(cartItemDTO.addedAt());
        
        return cartItem;
    }
}
package com.ming.shopdemo.cart.service.impl;

import com.ming.shopdemo.cart.model.Cart;
import com.ming.shopdemo.cart.model.CartItem;
import com.ming.shopdemo.cart.model.dto.CartDto;
import com.ming.shopdemo.cart.model.mapper.CartMapper;
import com.ming.shopdemo.cart.repository.CartItemRepository;
import com.ming.shopdemo.cart.repository.CartRepository;
import com.ming.shopdemo.cart.service.CartService;
import com.ming.shopdemo.common.exception.InvalidArgumentException;
import com.ming.shopdemo.product.model.Product;
import com.ming.shopdemo.product.model.dto.ProductDto;
import com.ming.shopdemo.product.model.mapper.ProductMapper;
import com.ming.shopdemo.useraccount.model.UserAccount;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductMapper productMapper;

    private final CartMapper cartMapper;

    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public void addItemToCart(ProductDto productDto, int quantity) {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = userAccount.getCart();

        if (cart == null) {
            createCart(userAccount);
            cart = userAccount.getCart();
        }

        Product product = productMapper.toEntity(productDto);

        // check CartItem exists
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(CartItem.builder().cart(cart).product(product).quantity(0).build());

        // check stock enough
        int newQuantity = cartItem.getQuantity() + quantity;

        if (newQuantity > product.getStock()) {
            throw new InvalidArgumentException("stock is not enough. stock is " + product.getStock());
        }

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartDto getCart() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = userAccount.getCart();

        if (cart == null) {
            createCart(userAccount);
            cart = userAccount.getCart();
        }

        return cartMapper.toDto(cart, userAccount);
    }

    @Transactional
    @Override
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        if (quantity <= 0) {
            deleteCartItemById(cartItemId);
        }

        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartItem cartItem = userAccount.getCart().getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId)).findFirst()
                .orElseThrow(() -> new InvalidArgumentException("cart item not found"));


        int productStock = cartItem.getProduct().getStock();
        if (quantity > productStock) {
            throw new InvalidArgumentException("stock is not enough. stock is " + productStock);
        }
        cartItemRepository.updateQuantityById(cartItemId, quantity);
    }

    @Transactional
    @Override
    public void deleteCartItemById(Long cartItemId) {
        cartItemRepository.deleteCartItemById(cartItemId);
    }

    @Transactional
    @Override
    public void clearCart() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = userAccount.getCart();
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    private void createCart(UserAccount userAccount) {
        Cart cart = new Cart();
        cart.setUserAccount(userAccount);
        userAccount.setCart(cart);
        cartRepository.save(cart);
    }

}

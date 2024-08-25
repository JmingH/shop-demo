package com.ming.shopdemo.cart.model.mapper;

import com.ming.shopdemo.cart.model.Cart;
import com.ming.shopdemo.cart.model.CartItem;
import com.ming.shopdemo.cart.model.dto.CartDto;
import com.ming.shopdemo.cart.model.dto.CartItemDto;
import com.ming.shopdemo.product.model.mapper.ProductMapper;
import com.ming.shopdemo.useraccount.model.UserAccount;
import com.ming.shopdemo.useraccount.model.mapper.UserAccountMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {UserAccountMapper.class, ProductMapper.class})
public interface CartMapper {
    @Mapping(target = "userAccountDto", source = "userAccount")
    CartDto toDto(Cart cart, UserAccount userAccount);

    @Mapping(target = "productDto", source = "product")
    CartItemDto toCartItemDto(CartItem cartItem);
}

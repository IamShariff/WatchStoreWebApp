package com.watchhub.watchstore.entitytransformer;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.entity.Cart;
import com.watchhub.watchstore.dto.CartDto;
import com.watchhub.watchstore.dto.WatchDto;

/**
 * The CartTransformer class is responsible for transforming Cart objects into
 * CartDto objects.
 * 
 * @author yogesh04
 */
@Component
public class CartTransformer implements Function<Cart, CartDto> {

	@Override
	public CartDto apply(Cart cart) {
		WatchDto watchDto = new WatchDto();
		CartDto cartDto = new CartDto();
		cartDto.setCartId(cart.getCartId());
		cartDto.setWatchQty(cart.getWatchQty());
		watchDto.setModelNumber(cart.getWatch().getModelNumber());
		watchDto.setWatchName(cart.getWatch().getWatchName());
		watchDto.setWatchBrand(cart.getWatch().getWatchBrand());
		watchDto.setPrice(cart.getWatch().getPrice());
		watchDto.setStockQuantity(cart.getWatch().getStockQuantity());
		watchDto.setWatchType(cart.getWatch().getWatchType().toString());
		watchDto.setAvailableStatus(cart.getWatch().isAvailableStatus());
		watchDto.setImagePathList(
				cart.getWatch().getImages().stream().map((image) -> image.getImagePath()).collect(Collectors.toList()));
		cartDto.setWatch(watchDto);
		return cartDto;
	}

}

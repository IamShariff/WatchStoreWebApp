package com.watchhub.watchstore.controller.impl;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import com.watchhub.watchstore.controller.CartController;
import com.watchhub.watchstore.dto.CartDto;
import com.watchhub.watchstore.dto.CartSaveDto;
import com.watchhub.watchstore.dto.CartUpdateDto;
import com.watchhub.watchstore.entity.Cart;
import com.watchhub.watchstore.extractor.UserDetailExtractor;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.CartService;

import lombok.RequiredArgsConstructor;

/**
 * The implementation class of the CartController interface. This class provides
 * the actual implementation for managing carts.
 * 
 * @author yogesh04
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

	private final CartService cartService;

	private final Function<Cart, CartDto> cartTransformer;

	private final Predicate<CartSaveDto> cartDtoValidator;

	private final Predicate<CartUpdateDto> updateCartDtoValidator;

	private final Function<String, Integer> stringToIntParser;
	
	private final UserDetailExtractor userDetailExactrator;

	private static final Logger logger = LoggerFactory.getLogger(CartControllerImpl.class);

//	@Autowired
//	public CartControllerImpl(CartService cartService, Function<Cart, CartDto> cartTransformer,
//			Predicate<CartSaveDto> cartDtoValidator, Predicate<CartUpdateDto> updateCartDtoValidator,
//			Function<String, Integer> stringToIntParser, UserDetailExtractor userDetailExactrator) {
//		super();
//		this.cartService = cartService;
//	//	this.cartTransformer = cartTransformer;
//		this.cartDtoValidator = cartDtoValidator;
//		this.updateCartDtoValidator = updateCartDtoValidator;
//		//this.stringToIntParser = stringToIntParser;
//		this.userDetailExactrator = userDetailExactrator;
//	}

	@Override
	public ResponseEntity<List<CartDto>> getCartsByUserId() {
		UserDetails userDetails =(UserDetails) this.userDetailExactrator.getPrincipal();
		logger.info("Request for carts by user with ID: " + userDetails.getUsername());
		List<CartDto> cartsDto = cartService.retrieveCartByUserId(userDetails.getUsername()).stream().map(cartTransformer::apply)
				.collect(Collectors.toList());
		logger.info("Returning carts response for user with ID: " + userDetails.getUsername());
		return new ResponseEntity<>(cartsDto, HttpStatus.OK);
	}

	
	@Override
	public ResponseEntity<ApiResponse> createCart(CartSaveDto cartDto) {
		UserDetails userDetails =(UserDetails) this.userDetailExactrator.getPrincipal();
		logger.info("Creating cart for user with ID: " + userDetails.getUsername());
		cartDtoValidator.test(cartDto);
		cartService.addCart(userDetails.getUsername(), cartDto.getWatchModel());
		logger.info("Watch added successfully to cart for user with ID: " + userDetails.getUsername());
		ApiResponse customResponse = new ApiResponse("Watch added successfully");
		return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ApiResponse> updateCart(String cartId, CartUpdateDto cartUpdateDto) {
		logger.info("Updating cart with ID: " + cartId);
		updateCartDtoValidator.test(cartUpdateDto);
		cartService.updateCartQty(stringToIntParser.apply(cartId), cartUpdateDto.getWatchQty());
		logger.info("Watch updated successfully for cart with ID: " + cartId);
		ApiResponse customResponse = new ApiResponse("Watch updated successfully");
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> removeCartById(String cartId) {
		logger.info("Removing cart with ID: " + cartId);
		int validCartId = stringToIntParser.apply(cartId);
		cartService.deleteCartById(validCartId);
		logger.info("Watch deleted successfully for cart with ID: " + cartId);
		ApiResponse customResponse = new ApiResponse("Watch deleted successfully");
		return new ResponseEntity<>(customResponse, HttpStatus.ACCEPTED);
	}

}

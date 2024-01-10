package com.watchhub.watchstore.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.watchhub.watchstore.controller.AddressController;
import com.watchhub.watchstore.dto.AddressDto;
import com.watchhub.watchstore.entity.Address;
import com.watchhub.watchstore.extractor.UserDetailExtractor;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.AddressService;

import ch.qos.logback.classic.Logger;

/**
 * @author deepankarbatra
 * 
 *         This class implements the AddressController interface and provides
 *         the endpoints for managing addresses.
 */
@RestController
public class AddressControllerImpl implements AddressController {

	private Function<AddressDto, Address> addressDtoTransformer;

	private Function<Address, AddressDto> addressEntityTransformer;

	private Predicate<AddressDto> addressDtoValidator;

	private AddressService addressService;
	
	private UserDetailExtractor userDetailExtractor;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CartControllerImpl.class);

	/**
	 * Constructs an instance of AddressControllerImpl with the necessary
	 * dependencies.
	 *
	 * @param addressDtoTransformer          The transformer function to convert
	 *                                       AddressDto to Address.
	 * @param addressService                 The AddressService for address-related
	 *                                       operations.
	 * @param addressEntityTransformer       The transformer function to convert
	 *                                       Address to AddressDto.
	 * @param addressDtoValidator            The validator predicate for
	 *                                       AddressDto.
	 * @param userDetailExtractor            Extracts user details 
	 */
	@Autowired
	AddressControllerImpl(Function<AddressDto, Address> addressDtoTransformer, AddressService addressService,
			Function<Address, AddressDto> addressEntityTransformer,
			Predicate<AddressDto> addressDtoValidator,
			UserDetailExtractor userDetailExtractor) {
		this.addressDtoTransformer = addressDtoTransformer;
		this.addressService = addressService;
		this.addressEntityTransformer = addressEntityTransformer;
		this.addressDtoValidator = addressDtoValidator;
		this.userDetailExtractor = userDetailExtractor;
	}

	@Override
	public ResponseEntity<ApiResponse> save(AddressDto addressDto) {
		addressDtoValidator.test(addressDto);
		logger.info("saving addresses : {}", addressDto);
		Address addressTransform = addressDtoTransformer.apply(addressDto);
		final UserDetails currentUser = this.userDetailExtractor.getPrincipal();
		final String userId = currentUser.getUsername();
		logger.info("User Id : {}", userId);
		this.addressService.save(addressTransform, userId);
		ApiResponse saveResponse = new ApiResponse("Address added successfully!");
		return new ResponseEntity<>(saveResponse, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<AddressDto>> get(String userId) {
		logger.info("Getting addresses for user ID: {}", userId);
		List<AddressDto> address = new ArrayList<>();
		this.addressService.get(userId)
				.forEach(addressRes -> address.add(this.addressEntityTransformer.apply(addressRes)));

		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> update(AddressDto addressDto, long addressId) {
		logger.info("Updating address with ID: {}, Data: {}", addressId, addressDto);
		addressDtoValidator.test(addressDto);
		Address addressTransform = this.addressDtoTransformer.apply(addressDto);
		this.addressService.update(addressId, addressTransform);
		ApiResponse updateResponse = new ApiResponse("Address updated successfully");
		return new ResponseEntity<>(updateResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> delete(long addressId) {
		logger.info("Deleting address with ID: {}", addressId);
		this.addressService.delete(addressId);
		ApiResponse deleteResponse = new ApiResponse("Address deleted successfully");
		return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
	}
}

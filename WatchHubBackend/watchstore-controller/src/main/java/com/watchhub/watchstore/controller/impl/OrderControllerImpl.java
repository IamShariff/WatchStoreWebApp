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
import org.springframework.web.bind.annotation.RestController;

import com.watchhub.watchstore.controller.OrderController;
import com.watchhub.watchstore.dto.OrderDto;
import com.watchhub.watchstore.dto.OrderSaveDto;
import com.watchhub.watchstore.dto.OrderSummaryDto;
import com.watchhub.watchstore.dto.StatusDto;
import com.watchhub.watchstore.dtotransformer.StatusDtoTransformer;
import com.watchhub.watchstore.entity.Order;
import com.watchhub.watchstore.enums.UserRole;
import com.watchhub.watchstore.extractor.UserDetailExtractor;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.OrderService;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * Implementation of the OrderController interface. Handles the REST API
 * endpoints for managing orders.
 * 
 * @author tushar01
 */
@RestController
public class OrderControllerImpl implements OrderController {

	private final OrderService orderService;
	private final Predicate<OrderSaveDto> orderSaveDtoValidator;
	private final Function<Order, OrderSummaryDto> orderSummaryTransformer;
	private final Function<Order, OrderDto> orderTransformer;
	private final Predicate<StatusDto> statusDtoValidator;
	private final UserDetailExtractor userDetailExactrator;
	private final StatusDtoTransformer statusDtoTransformer;

	private static final Logger logger = LoggerFactory.getLogger(OrderControllerImpl.class);

	@Autowired
	public OrderControllerImpl(final OrderService orderService, final Predicate<OrderSaveDto> orderSaveDtoValidator,
			final Function<Order, OrderSummaryDto> orderSummaryTransformer,
			final Function<Order, OrderDto> orderTransformer, final Predicate<StatusDto> statusDtoValidator,
			final UserDetailExtractor userDetailExactrator, final StatusDtoTransformer statusDtoTransformer) {
		super();
		this.orderService = orderService;
		this.orderSaveDtoValidator = orderSaveDtoValidator;
		this.orderSummaryTransformer = orderSummaryTransformer;
		this.orderTransformer = orderTransformer;
		this.statusDtoValidator = statusDtoValidator;
		this.userDetailExactrator = userDetailExactrator;
		this.statusDtoTransformer = statusDtoTransformer;
	}

	@Override
	public ResponseEntity<ApiResponse> book(final OrderSaveDto orderSaveDto) {
		this.orderSaveDtoValidator.test(orderSaveDto);
		final String userEmailId = this.userDetailExactrator.getPrincipal().getUsername();
		logger.info(Constant.REQUEST_PLACE_ORDER + orderSaveDto.getWatchModelNumber());
		final Order order = this.orderService.add(orderSaveDto.getWatchModelNumber(), orderSaveDto.getAddressId(),
				userEmailId, orderSaveDto.getQuantity());
		logger.info(Constant.ORDER_BOOKED + order.getId());
		final String message = Constant.ORDER_BOOKED + order.getId();
		final ApiResponse response = new ApiResponse(message);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<List<OrderSummaryDto>> fetchAll() {
		final String userEmailId = this.userDetailExactrator.getPrincipal().getUsername();
		final UserRole userRole = this.userDetailExactrator.getRole();
		logger.info(Constant.REQUEST_ALL_ORDERS);
		final List<OrderSummaryDto> ordersSummaryDto = this.orderService.findAllByUser(userEmailId, userRole).stream()
				.map(orderSummaryTransformer::apply).collect(Collectors.toList());
		logger.info(Constant.RESPONSE_ALL_ORDERS);
		return ResponseEntity.ok(ordersSummaryDto);
	}

	@Override
	public ResponseEntity<ApiResponse> updateOrderStatus(String id, StatusDto status) {
		this.statusDtoValidator.test(status);
		logger.info(Constant.REQUEST_UPDATE_STATUS);
		this.orderService.updateOrderStatus(id, this.statusDtoTransformer.apply(status));
		logger.info(Constant.STATUS_UPDATED);
		final ApiResponse response = new ApiResponse(Constant.STATUS_UPDATED);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@Override
	public ResponseEntity<OrderDto> fetchById(String orderId) {
		final String userEmailId = this.userDetailExactrator.getPrincipal().getUsername();
		final UserRole userRole = this.userDetailExactrator.getRole();
		logger.info(Constant.REQUEST_FETCH_ORDER + orderId);
		final Order order = this.orderService.findById(orderId, userEmailId, userRole);
		logger.info(Constant.RESPONSE_FETCH_ORDER + orderId);
		return ResponseEntity.ok(this.orderTransformer.apply(order));
	}

}

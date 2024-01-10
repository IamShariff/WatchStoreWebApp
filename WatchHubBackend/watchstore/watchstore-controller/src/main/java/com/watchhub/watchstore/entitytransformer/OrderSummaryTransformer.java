package com.watchhub.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watchhub.watchstore.entity.Order;
import com.watchhub.watchstore.entity.User;
import com.watchhub.watchstore.entity.Watch;
import com.watchhub.watchstore.dto.OrderStatusDto;
import com.watchhub.watchstore.dto.OrderSummaryDto;
import com.watchhub.watchstore.dto.UserProfileDto;
import com.watchhub.watchstore.dto.WatchDto;

/**
 * The OrderSummaryTransformer class is a functional component that transforms an Order entity into an OrderSummaryDto.
 * 
 * @author tushar01
 */
@Component
public class OrderSummaryTransformer implements Function<Order, OrderSummaryDto> {

	private final Function<Watch, WatchDto> watchTransformer;
	private final Function<User, UserProfileDto> userProfileTransformer;

	@Autowired
	public OrderSummaryTransformer(Function<Watch, WatchDto> watchTransformer,
			Function<User, UserProfileDto> userProfileTransformer) {
		super();
		this.watchTransformer = watchTransformer;
		this.userProfileTransformer = userProfileTransformer;
	}

	@Override
	public OrderSummaryDto apply(final Order order) {
		final OrderSummaryDto orderSummarytDto = new OrderSummaryDto();
		orderSummarytDto.setAmount(order.getAmount());
		orderSummarytDto.setId(order.getId());
		orderSummarytDto.setOrderStatus(OrderStatusDto.valueOf(order.getStatus().toString()));
		orderSummarytDto.setUser(this.userProfileTransformer.apply(order.getUser()));
		orderSummarytDto.setQuantity(order.getQuantiy());
		orderSummarytDto.setWatch(this.watchTransformer.apply(order.getWatch()));
		return orderSummarytDto;
	}
}

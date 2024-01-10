package com.watchhub.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.AddressDto;
import com.watchhub.watchstore.dto.OrderDto;
import com.watchhub.watchstore.dto.OrderStatusDto;
import com.watchhub.watchstore.dto.UserProfileDto;
import com.watchhub.watchstore.dto.WatchDto;
import com.watchhub.watchstore.entity.DeliveryAddress;
import com.watchhub.watchstore.entity.Order;
import com.watchhub.watchstore.entity.User;
import com.watchhub.watchstore.entity.Watch;

/**
 * The OrderTransformer class is a functional component that transforms an Order entity into an OrderDto.
 * 
 * @author tushar01
 */
@Component
public class OrderTransformer implements Function<Order, OrderDto> {

	private final Function<User, UserProfileDto> userProfileTransformer;
	private final Function<Watch, WatchDto> watchTransformer;

	@Autowired
	public OrderTransformer(Function<User, UserProfileDto> userProfileTransformer,
			Function<Watch, WatchDto> watchTransformer) {
		super();
		this.userProfileTransformer = userProfileTransformer;
		this.watchTransformer = watchTransformer;
	}
	

	@Override
	public OrderDto apply(final Order order) {
		final OrderDto orderDto = new OrderDto();
		orderDto.setAmount(order.getAmount());
		AddressDto addressDto= convertDeliveryAddressToAddressDto(order.getDeliveryAddress());
		orderDto.setDeliveryAddress(addressDto);
		orderDto.setId(order.getId());
		OrderStatusDto orderStatusDto=OrderStatusDto.valueOf(order.getStatus().toString());
		orderDto.setOrderStatus(orderStatusDto);
		orderDto.setUser(this.userProfileTransformer.apply(order.getUser()));
		orderDto.setWatch(this.watchTransformer.apply(order.getWatch()));
		orderDto.setQuantity(order.getQuantiy());
		orderDto.setTimestamp(order.getTimestamp());
		orderDto.setStatusTimestamp(order.getStatusTimestamp());
		return orderDto;
	}


	private AddressDto convertDeliveryAddressToAddressDto(DeliveryAddress deliveryAddress) {
		AddressDto addressDto=new AddressDto();
		addressDto.setAddressId(deliveryAddress.getId());
		addressDto.setCity(deliveryAddress.getCity());
		addressDto.setCountry(deliveryAddress.getCountry());
		addressDto.setLandmark(deliveryAddress.getLandmark());
		addressDto.setPhoneNumber(deliveryAddress.getPhoneNumber());
		addressDto.setPincode(String.valueOf(deliveryAddress.getPincode()));
		addressDto.setState(deliveryAddress.getState());
		addressDto.setStreetName(deliveryAddress.getStreetName());
		return addressDto;
	}


}

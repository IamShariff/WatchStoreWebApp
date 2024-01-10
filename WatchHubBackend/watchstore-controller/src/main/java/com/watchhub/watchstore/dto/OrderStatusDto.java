package com.watchhub.watchstore.dto;

import java.util.Arrays;

import com.watchhub.watchstore.exceptions.BadRequestException;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * The OrderStatusDto enum represents the DTO (Data Transfer Object) for OrderStatus.
 * 
 * @author tushar01
 */

public enum OrderStatusDto {
	BOOKED("BOOKED"), IN_PROGRESS("IN_PROGRESS"), IN_SHIPMENT("IN_SHIPMENT"), DELIVERED("DELIVERED");

	String value;

	OrderStatusDto(String value) {
		this.value = value;
	}
	
	 /**
     * Retrieves the OrderStatus based on the provided order status string.
     *
     * @param orderStatus the order status string
     * @return the corresponding OrderStatus
     * @throws BadRequestException if the order status string is not valid
     */
	public static OrderStatusDto fromString(String orderStatus) {
		return Arrays.stream(values()).filter(status -> status.value.equalsIgnoreCase(orderStatus)).findFirst()
				.orElseThrow(() -> new BadRequestException("status", Constant.STATUS_ENUM_NOT_VALID));
	}

}

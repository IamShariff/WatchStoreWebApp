package com.watchhub.watchstore.dtotransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.StatusDto;
import com.watchhub.watchstore.enums.OrderStatus;

/**
 * The StatusDtoTransformer class is responsible for transforming status dto 
 * into OrderStatus.
 * 
 * @author tushar01
 */

@Component
public class StatusDtoTransformer implements Function<StatusDto, OrderStatus>{

	@Override
	public OrderStatus apply(StatusDto statusDto) {
		
		return OrderStatus.valueOfStatus(statusDto.getStatus().toString());
	}

}

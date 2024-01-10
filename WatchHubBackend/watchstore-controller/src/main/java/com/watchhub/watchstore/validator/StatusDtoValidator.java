package com.watchhub.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.StatusDto;
import com.watchhub.watchstore.exceptions.BadRequestException;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * The StatusDtoValidator class is a validator that validates a StatusDto object.
 * It checks if the order status is null, and throws a BadRequestException if it is.
 * 
 * @author tushar01
 */
@Component
public class StatusDtoValidator implements Predicate<StatusDto> {

	@Override
	public boolean test(StatusDto statusDto) {
		if (statusDto == null||statusDto.getStatus()==null)
			throw new BadRequestException("status", Constant.ORDER_STATUS_REQUIRED);
		return true;
	}

}

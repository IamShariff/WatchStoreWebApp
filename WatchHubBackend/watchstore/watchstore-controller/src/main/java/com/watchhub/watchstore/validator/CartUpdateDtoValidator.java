package com.watchhub.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.CartUpdateDto;
import com.watchhub.watchstore.exceptions.BadRequestException;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * The CartUpdateDtoValidator class is responsible for validating CartSaveDto
 * objects.
 * 
 * @author yogesh04
 */
@Component
public class CartUpdateDtoValidator implements Predicate<CartUpdateDto>{

	@Override
	public boolean test(CartUpdateDto cartUpdateDto) {
		
		if (cartUpdateDto.getWatchQty() < 0) {
		    throw new BadRequestException("CartQty", Constant.INVALID_QUANTITY);
		}
		
		if (cartUpdateDto.getWatchQty() == 0) {
		    throw new BadRequestException("CartQty", Constant.QUANTITY_REQUIRED);
		}
		return true;
	}

}

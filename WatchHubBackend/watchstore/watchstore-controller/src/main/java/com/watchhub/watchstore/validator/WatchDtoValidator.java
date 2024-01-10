package com.watchhub.watchstore.validator;

import java.util.ArrayList;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import com.watchhub.watchstore.dto.WatchDto;
import com.watchhub.watchstore.exceptions.BadRequestException;

/**
 * Validator class for validating a WatchDto object.
 * 
 * @author karan
 */
@Component
public class WatchDtoValidator implements Predicate<WatchDto> {

	@Override
	public boolean test(WatchDto watchDto) {
		ArrayList<String> fieldNameList = new ArrayList<String>();
		ArrayList<String> fieldErrorList = new ArrayList<String>();
		if (watchDto.getModelNumber() == null || watchDto.getModelNumber().isBlank()) {
			fieldNameList.add("Watch model");
			fieldErrorList.add("Watch model is required");
		}

		if (watchDto.getWatchBrand() == null || watchDto.getWatchBrand().isBlank()) {
			fieldNameList.add("Watch Brand");
			fieldErrorList.add("Watch Brand is required");
		}

		if (watchDto.getWatchName() == null || watchDto.getWatchName().isBlank()) {
			fieldNameList.add("Watch name");
			fieldErrorList.add("Watch name is required");
		}

		if (watchDto.getWatchType() == null || watchDto.getWatchType().isBlank()) {
			fieldNameList.add("Watch type");
			fieldErrorList.add("Watch type is required");
		}

		if (watchDto.getImagePathList() == null || watchDto.getImagePathList().isEmpty()) {
			fieldNameList.add("Image Path");
			fieldErrorList.add("At least one image path is required");
		}

		if (watchDto.getStockQuantity() <= 0) {
			fieldNameList.add("Stock quantity");
			if (watchDto.getStockQuantity() == 0) {
				fieldErrorList.add("stock quantity is required");
			} else if (watchDto.getStockQuantity() < 0) {
				fieldErrorList.add("stock cannot be less then zero");
			}
		}

		if (watchDto.getPrice() == null) {
			fieldNameList.add("Watch price");
			fieldErrorList.add("Watch price is required");

		} else {
			if (watchDto.getPrice().signum() <= 0) {
				fieldErrorList.add("Watch price cannot be less then or equal to zero");
			}
		}

		if (watchDto.getAvailableStatus() == null) {
			fieldNameList.add("Available status");
			fieldErrorList.add("Available status is required");
		}

		if (fieldErrorList.isEmpty() != true && fieldNameList.isEmpty() != true) {
			String fieldName = String.join(",", fieldNameList);
			String fieldError = String.join(",", fieldErrorList);

			throw new BadRequestException(fieldName, fieldError);
		}

		return true;
	}

}

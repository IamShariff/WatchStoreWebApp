package com.watchhub.watchstore.controller.impl;

import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.watchhub.watchstore.controller.UserController;
import com.watchhub.watchstore.dto.UserDto;
import com.watchhub.watchstore.dto.UserProfileDto;
import com.watchhub.watchstore.entity.User;
import com.watchhub.watchstore.exceptions.InvalidEmailArgumentException;
import com.watchhub.watchstore.exceptions.InvalidEmailFormatException;
import com.watchhub.watchstore.extractor.UserDetailExtractor;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.UserService;
import com.watchhub.watchstore.util.CommonValidator;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * Implementation of the UserController interface that handles user-related
 * endpoints.
 * 
 * @author vishal deswal
 * @version 1.0
 */
@RestController
public class UserControllerImpl implements UserController {

	private final UserService userService;
	private final Function<User, UserProfileDto> userProfileEntityTransformer;
	private final Predicate<UserDto> registerUserDtoValidator;
	private final Function<UserDto, User> registerUserDtoTransformer;
	private final UserDetailExtractor detailExactrator;

	private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

	/**
	 * Constructs a new instance of UserControllerImpl with the UserService
	 * dependency.
	 *
	 * @param userServiceImpl The UserService implementation to be used.
	 */
	@Autowired
	public UserControllerImpl(final UserService userService,
			final Function<User, UserProfileDto> userProfileEntityTransformer,
			final Predicate<UserDto> registerUserDtoValidator, final Function<UserDto, User> registerUserDtoTransformer,
			final UserDetailExtractor detailExactrator) {
		super();
		this.userService = userService;
		this.userProfileEntityTransformer = userProfileEntityTransformer;
		this.registerUserDtoValidator = registerUserDtoValidator;
		this.registerUserDtoTransformer = registerUserDtoTransformer;
		this.detailExactrator = detailExactrator;
	}

	@Override
	public ResponseEntity<ApiResponse> register(final UserDto userDto) {
		this.registerUserDtoValidator.test(userDto);
		User user = this.registerUserDtoTransformer.apply(userDto);

		logger.info(Constant.REQUEST_TO_REGISTER, user.getEmailId());
		String savedEmailId = userService.addUser(user);
		logger.info(Constant.RESPONSE_TO_REGISTER, user.getEmailId());
		ApiResponse response = new ApiResponse(Constant.USER_REGISTER_SUCCESSFULLY + savedEmailId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}



	@Override
	public ResponseEntity<UserProfileDto> getUserById(String userEmailID) {
		UserDetails userDetails =(UserDetails) this.detailExactrator.getPrincipal();
		// Getting user emailId
		String emailId = userDetails.getUsername();
		logger.info(Constant.REQUEST_FETCH_USER, userEmailID);

		if (CommonValidator.isEmailNotValid(userEmailID)) {
			logger.info(Constant.INVALID_EMAIL_FORMAT + emailId);
			throw new InvalidEmailFormatException("Email Id", Constant.INVALID_EMAIL_FORMAT + userEmailID);
		}

		if (emailId.equals(userEmailID) == false) {
			logger.info(Constant.INVALID_EMAIL_ARGUMENT + emailId);
			throw new InvalidEmailArgumentException("Email Id", Constant.INVALID_EMAIL_ARGUMENT);
		}

		User user = userService.findUserById(emailId);
		UserProfileDto userProfileDto = this.userProfileEntityTransformer.apply(user);

		logger.info(Constant.RESPONSE_FETCH_USER, emailId);

		return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
	
	}
}

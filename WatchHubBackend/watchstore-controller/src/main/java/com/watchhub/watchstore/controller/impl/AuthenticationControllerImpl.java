package com.watchhub.watchstore.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;
import com.watchhub.watchstore.controller.AuthenticationController;
import com.watchhub.watchstore.exceptions.AuthenticationFailedException;
import com.watchhub.watchstore.exceptions.InvalidEmailFormatException;
import com.watchhub.watchstore.exceptions.InvalidPasswordFormatException;
import com.watchhub.watchstore.jwtservice.JwtRequest;
import com.watchhub.watchstore.jwtservice.JwtService;
import com.watchhub.watchstore.response.ApiResponse;
import com.watchhub.watchstore.service.UserService;
import com.watchhub.watchstore.util.CommonValidator;
import com.watchhub.watchstore.util.constants.Constant;

import lombok.RequiredArgsConstructor;

/**
 * The implementation class of the AuthenticationController interface. This
 * class provides the actual implementation for authenticate user.
 *
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;


	@Override
	public ResponseEntity<ApiResponse> authenticateAndGetToken(final JwtRequest jwtRequest) {
		
		Authentication authenticate;
		if (CommonValidator.isEmailNotValid(jwtRequest.getEmail())) {
			throw new InvalidEmailFormatException("Email Id", Constant.INVALID_EMAIL_FORMAT + jwtRequest.getEmail());
		}

		else if (CommonValidator.isPasswordNotValid(jwtRequest.getPassword())) {
			throw new InvalidPasswordFormatException("Password",
					Constant.INVALID_PASSWORD_FORMAT + jwtRequest.getPassword());
		}

		try {
			authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
		} catch (AuthenticationException e) {

			userService.findUserById(jwtRequest.getEmail());
			throw new AuthenticationFailedException("Password", Constant.AUTHENTICATION_FAILED);
		}

		if (authenticate.isAuthenticated()) {

			String jwtToken = jwtService.generateToken(jwtRequest);
			ApiResponse apiResponse = new ApiResponse(jwtToken);
			return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

		}
		return null;
	}

}

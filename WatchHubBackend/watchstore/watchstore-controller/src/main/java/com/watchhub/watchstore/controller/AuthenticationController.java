package com.watchhub.watchstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.watchhub.watchstore.jwtservice.JwtRequest;
import com.watchhub.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author vishaldeswal
 *
 */
@CrossOrigin
@Tag(name = "Authentication API")
public interface AuthenticationController {
	
	/**
	 * 
	 * @param jwtRequest
	 * @return jwt token
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> authenticateAndGetToken(@RequestBody final JwtRequest jwtRequest);
	
}

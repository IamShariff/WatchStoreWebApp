package com.watchhub.watchstore.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dao.UserRepository;
import com.watchhub.watchstore.entity.User;
import com.watchhub.watchstore.exceptions.UserNotFoundException;
import com.watchhub.watchstore.util.constants.Constant;

/**
 * 
 * @author mdsharif,vishaldeswal
 * In this class, we are retrieving user details from userDao and assigning
 * them to the Spring Security's UserDetails object.
 *
 */
@Component
public class SpringUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userInfo = userRepository.findById(email);
		return userInfo.map(SpringUserDetails::new)
				.orElseThrow(()-> new UserNotFoundException("Email_Id", Constant.USER_NOT_FOUND + email));
	}

	





	

}

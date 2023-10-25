 package com.nexcodemm.lms.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nexcodemm.lms.model.entities.User;
import com.nexcodemm.lms.model.excepiton.BadRequestException;
import com.nexcodemm.lms.repository.UserRepository;
import com.nexcodemm.lms.security.UserPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));
		if(user == null) {
			throw new BadRequestException("User not found with this " + username);
		}
		
		return UserPrincipal.build(user);

		
	}

}

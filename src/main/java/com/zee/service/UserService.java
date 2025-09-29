package com.zee.service;

import com.zee.model.User;

public interface UserService {
	
	User findUserByJwtToken(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;

}

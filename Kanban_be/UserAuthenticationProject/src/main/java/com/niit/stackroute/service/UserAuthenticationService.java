package com.niit.stackroute.service;

import com.niit.stackroute.domain.UserAuthentication;
import com.niit.stackroute.exception.UserAlreadyExistsException;
import com.niit.stackroute.exception.UserNotFoundException;

import java.util.List;

public interface UserAuthenticationService {
    UserAuthentication saveUser(UserAuthentication user) throws UserAlreadyExistsException;
    UserAuthentication findByUserEmail(String email) throws UserNotFoundException;
    List <String> getAllUsers();

    UserAuthentication updateUser(String email,UserAuthentication user);
}

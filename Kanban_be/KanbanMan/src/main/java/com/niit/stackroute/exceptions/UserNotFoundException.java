package com.niit.stackroute.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "User not found with this Email ID")
public class UserNotFoundException extends Exception {
}

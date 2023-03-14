package com.niit.stackroute.service;

import com.niit.stackroute.domain.UserAuthentication;

import java.util.Map;

public interface SecurityTokenGenerator {
    Map<String,String> generateToken(UserAuthentication user);
}

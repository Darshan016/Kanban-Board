package com.niit.stackroute.service;

import com.niit.stackroute.domain.UserAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class jwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator{

    @Override
    public Map<String, String> generateToken(UserAuthentication user) {
        String jwtToken=null;
        jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"secretkey").compact();
        Map<String,String> map = new HashMap<>();
        map.put("token",jwtToken);
        return map;
    }
}

package com.niit.stackroute.proxy;

import com.niit.stackroute.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-authentication-service",url = "localhost:8085")
public interface UserProxy {

    @PostMapping("/register")
    public ResponseEntity<?> registeringUser(@RequestBody User user);

    @PostMapping("/login")
    public ResponseEntity<?> generatingToken(@RequestBody User user);

    @PostMapping("/updateUser/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User user);


}

package com.niit.stackroute.controller;

import com.niit.stackroute.domain.UserAuthentication;
import com.niit.stackroute.exception.UserAlreadyExistsException;
import com.niit.stackroute.exception.UserNotFoundException;
import com.niit.stackroute.service.SecurityTokenGenerator;
import com.niit.stackroute.service.UserAuthenticationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
//@RequestMapping("/api/v1/")
public class UserAuthenticationController {

    private final UserAuthenticationService userService;
    private final SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    private UserAuthenticationController(UserAuthenticationService userService, SecurityTokenGenerator securityTokenGenerator){
        this.userService=userService;
        this.securityTokenGenerator=securityTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registeringUser(@RequestBody UserAuthentication user) throws UserAlreadyExistsException {
        System.out.println("inside auth service");
        System.out.println(user);
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/registeredusers")
    public ResponseEntity<?> registeredusers(HttpServletRequest request){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> generatingToken(@RequestBody UserAuthentication user) throws UserNotFoundException {
        Map<String , String> map =null;
        ResponseEntity<?> responseEntity;
        try{
            UserAuthentication userObj = userService.findByUserEmail(user.getEmail());
            System.out.println(userObj);
            if (userObj.getEmail().equals(user.getEmail()) && userObj.getPassword().equals(user.getPassword())) {
                map = securityTokenGenerator.generateToken(userObj);
                map.put("userName",userObj.getUserName());
            }
           responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>("Token Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/updateUser/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email,@RequestBody UserAuthentication user) throws UserAlreadyExistsException {
        System.out.println(user);
        return new ResponseEntity<>(this.userService.updateUser(email,user), HttpStatus.CREATED);
    }
}

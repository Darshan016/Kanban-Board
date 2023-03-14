package com.niit.stackroute.controllers;

//import com.niit.stackroute.config.EmailDetails;
//import com.niit.stackroute.config.Producer;

import com.niit.stackroute.config.EmailDetails;
import com.niit.stackroute.config.OTP;
import com.niit.stackroute.config.Producer;
import com.niit.stackroute.domain.ImageModel;
import com.niit.stackroute.domain.User;
import com.niit.stackroute.exceptions.UserAlreadyExistsException;
import com.niit.stackroute.exceptions.UserNotFoundException;
import com.niit.stackroute.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final Producer producer;

    @Value("${project.image}")
    private String path;

    @Autowired
    public UserController(UserService userService, Producer producer) {
        this.userService = userService;
        this.producer = producer;
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {

        System.out.println("abababaabab");
        EmailDetails emailDetails = new EmailDetails(user.getEmail(), user.getUserName(), "YOU REGISTRATION HAS DONE SUCCESSFULLY", "KANBAN APPLICATION");
//        producer.sendTaskToRabbitMq(emailDetails);
//        String fileName = null;
//        try {
//            fileName = this.userService.uploadImage(path, image);
//        } catch (IOException e) {
//
//            throw new RuntimeException(e);
//        }

//        ImageModel imageModel = new ImageModel(fileName, "Image is sucessfully uploaded");
//        user.setImageModel(imageModel);
        return new ResponseEntity<>(this.userService.saveUser(user), HttpStatus.CREATED);

    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("image") MultipartFile image) {


        String fileName = null;
        try {
            fileName = this.userService.uploadImage(path, image);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        ImageModel imageModel = new ImageModel(fileName, "Image is sucessfully uploaded");

        return new ResponseEntity<>(imageModel, HttpStatus.CREATED);

    }

    @GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void servefile(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream stream= this.userService.getImage(path, imageName);
   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(stream,response.getOutputStream());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws UserNotFoundException, InterruptedException {
        try {
            {
                System.out.println("inside controllr if");
                if (this.userService.getUserByEmail(user).equals(null)) {
                    return new ResponseEntity<>("Invalid Credentials", HttpStatus.CONFLICT);
                } else {
//                    EmailDetails emailDetails = new EmailDetails(user.getEmail(), user.getUserName(), "YOU REGISTRATION HAS DONE SUCCESSFULLY", "KANBAN APPLICATION");
//                    producer.sendTaskToRabbitMq(emailDetails);
                    return new ResponseEntity<>(this.userService.getUserByEmail(user), HttpStatus.OK);
                }
            }

        } catch (UserNotFoundException unf) {
            throw new UserNotFoundException();
        }
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @PostMapping("/user/otp/{email}")
    public ResponseEntity<?> OTP(@PathVariable String email) {
        String sendOtp = userService.OTP(email);
        System.out.println("OTP " + sendOtp);
        if (sendOtp != null) {
            OTP otp = new OTP(email, sendOtp);
            producer.sendOTP(otp);
            return new ResponseEntity<>(sendOtp, HttpStatus.OK);

        } else {
            return null;

        }
    }

    @PostMapping("/updateUser/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User user) throws UserNotFoundException {

        return new ResponseEntity<>(userService.updateUser(email, user), HttpStatus.OK);
    }

}

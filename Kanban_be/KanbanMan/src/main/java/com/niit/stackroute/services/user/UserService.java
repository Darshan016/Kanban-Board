package com.niit.stackroute.services.user;

import com.niit.stackroute.domain.User;
import com.niit.stackroute.exceptions.UserAlreadyExistsException;
import com.niit.stackroute.exceptions.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    Object getUserByEmail(User user) throws UserNotFoundException;
    User getUser(String email);

    User updateUser(String email,User user)throws UserNotFoundException;

    String OTP(String email) ;

    String uploadImage(String path, MultipartFile file) throws IOException;

InputStream getImage(String path,String fileName) throws FileNotFoundException;
    Optional<User> getByEmail(String email);
}

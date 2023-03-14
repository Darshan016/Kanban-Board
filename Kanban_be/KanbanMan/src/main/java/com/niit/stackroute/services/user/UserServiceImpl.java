package com.niit.stackroute.services.user;

import com.niit.stackroute.config.OTP;
import com.niit.stackroute.domain.User;
import com.niit.stackroute.exceptions.UserAlreadyExistsException;
import com.niit.stackroute.exceptions.UserNotFoundException;
import com.niit.stackroute.proxy.UserProxy;
import com.niit.stackroute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Path;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProxy userProxy;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProxy userProxy) {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findById(email);
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        System.out.println(user);
        System.out.println("line 26");

        ResponseEntity<?> responseEntity = userProxy.registeringUser(user);
        System.out.println(responseEntity.getBody());
        return this.userRepository.save(user);
    }

    @Override
    public Object getUserByEmail(User user) throws UserNotFoundException {
        ResponseEntity<?> responseEntity = userProxy.generatingToken(user);
        System.out.println("inside service");
        System.out.println(responseEntity.getBody());
        User user1 = this.userRepository.findByEmail(user.getEmail());
        if (user.getEmail().equals(user1.getEmail()) && user.getPassword().equals(user1.getPassword()))
            return responseEntity.getBody();
        else
            return null;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(String email, User user) throws UserNotFoundException {
        User user1 = userRepository.findById(email).get();
        if (user1.getEmail() != null) {
            user1.setEmail(email);
            user1.setPassword(user.getPassword());
            user1.setUserName(user1.getUserName());
            ResponseEntity response = userProxy.updateUser(email, user);

            System.out.println(response);
            System.out.println("user: " + user1);
            return userRepository.save(user1);
        }
        throw new UserNotFoundException();

    }


    @Override
    public String OTP(String email) {
        if (userRepository.findById(email).isPresent()) {
            int min = 1;
            int max = 9;
            int count = 0;

            List<Integer> list = new ArrayList<>();

            while (count < 4) {
                int otp = (int) ((Math.random()) * (max - min + 1) + min);

                list.add(otp);
                count++;
            }

            String list1 = list.toString().replaceAll("\\[|\\]", "").replaceAll(", ", "");
            OTP otp = new OTP(email, list1);

            return list1;
        }
        return null;
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String filePath = path + File.separator + name;
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
       String fullPath=path+File.separator+fileName;
       InputStream inputStream=new FileInputStream(fullPath);
       return inputStream;
    }

    public static void main(String[] args) {
        //   System.out.println(UserServiceImpl.OTP(""));
    }
}

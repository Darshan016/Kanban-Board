package com.niit.stackroute.repositorytest;

import com.niit.stackroute.domain.User;
import com.niit.stackroute.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    public void setUp(){
        user = new User("abc@gmail.com","username","password","1234567899","abc","abc");
    }
    @AfterEach
    public void tearDown(){
        user=null;
    }
    @Test
//    @DisplayName("test for saving")
    public void save(){
        userRepository.save(user);
        User user1=userRepository.findById(user.getEmail()).get();
        assertNotNull(user1);
        assertEquals(user.getEmail(),user1.getEmail());
    }
//    @Test
//    public void fetch(){
//        User user1=new User("abc@gmail.com","username","password","1236547896","abc","abc");
//        List<User> userList=userRepository.findAll();
//        assertEquals(3,userList.size());
//    }
    @Test
    public void fetchbyid(){
        userRepository.save(user);
        User user1=userRepository.findById(user.getEmail()).get();
        assertEquals(user,user1);
    }
}

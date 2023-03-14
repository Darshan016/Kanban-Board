package com.niit.stackroute.servicetest;

import com.niit.stackroute.domain.User;
import com.niit.stackroute.exceptions.UserNotFoundException;
import com.niit.stackroute.repository.UserRepository;
import com.niit.stackroute.services.user.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    List<User> userList=new ArrayList<>();
    @BeforeEach
    public void setUp(){
        user=new User("abc@gmail.com","username1","password","1234569874","abc","abc");
    }
    @AfterEach
    public void tearDown(){
        user=null;
    }


    @Test
    public void savecheck(){
        userRepository.save(user);
//        User user1=userRepository.findById(user.getEmail()).get();
//        assertNotNull(user1);
        assertEquals("abc@gmail.com",user.getEmail());
    }
    @Test
    public void getAllCheck(){
        User user1=new User("email@gmail.com","username2","password2","1478523698","anc","abc");
        List<User> userList1=new ArrayList<>();
        userList1.add(user1);
        userList1=userRepository.findAll();
        int size=userList1.size();
        assertEquals(size,userList1.size());
    }
    @Test
    public void getAllFailure(){
        User user1=new User("email@gmail.com","username","password","4569871230","abc","abc");
        List<User> userList1=userRepository.findAll();
        assertNotEquals(5,userList1.size());


    }
    @Test
    public void getEmailCheck() throws UserNotFoundException {

        given(userRepository.findById(user.getEmail())).willReturn(Optional.of(user));
        User user1=userService.getByEmail(user.getEmail()).get();
        assertThat(user1).isNotNull();
    }
}

package com.niit.stackroute.service;

import com.niit.stackroute.domain.UserAuthentication;
import com.niit.stackroute.exception.UserAlreadyExistsException;
import com.niit.stackroute.repository.UserAuthenticationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;

    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationServiceImpl;
    private UserAuthentication user1, user2;
    List<UserAuthentication> userList;

    @BeforeEach
    public void setup(){
        user1= new UserAuthentication("jt","jt","jt");
        user2= new UserAuthentication("abc","abc","jt");
        userList=Arrays.asList(user1);
    }

    @AfterEach
    public void tearDown(){
        user1=null;
    }

    @Test
    public void givenUserToSaveReturnSavedUserSuccess() throws UserAlreadyExistsException {

        when(userAuthenticationRepository.findById(user1.getEmail())).thenReturn(Optional.ofNullable(null));
        when(userAuthenticationRepository.save(any())).thenReturn(user1);
        assertEquals(user1,userAuthenticationServiceImpl.saveUser(user1));
        verify(userAuthenticationRepository,times(1)).save(any());
        verify(userAuthenticationRepository,times(1)).findById(any());

    }

    @Test
    public void givenUserToSaveReturnUserFailure(){

        when(userAuthenticationRepository.findById(user1.getEmail())).thenReturn(Optional.ofNullable(user1));

        assertThrows(UserAlreadyExistsException.class,()->userAuthenticationServiceImpl.saveUser(user1));

        verify(userAuthenticationRepository,times(0)).save(any());
        verify(userAuthenticationRepository,times(1)).findById(any());
    }
}

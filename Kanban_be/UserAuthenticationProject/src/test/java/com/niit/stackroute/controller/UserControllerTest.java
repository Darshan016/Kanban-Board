package com.niit.stackroute.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.stackroute.domain.UserAuthentication;
import com.niit.stackroute.exception.UserAlreadyExistsException;
import com.niit.stackroute.service.UserAuthenticationService;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserAuthenticationService userAuthenticationService;
    private UserAuthentication user1,user2;
    List<UserAuthentication> userList;

    @InjectMocks
    private UserAuthenticationController userAuthenticationController;

    @BeforeEach
    public  void setUp(){
        user1=new UserAuthentication("jt","jt","jt");
        user2=new UserAuthentication("jt1","jt1","jt");
        userList = Arrays.asList(user1,user2);
        mockMvc= MockMvcBuilders.standaloneSetup(userAuthenticationController).build();
    }

    @AfterEach
    public void tearDown(){
        user1=null;
        user2=null;
    }

    @Test
    public void givenUserTl065toSaveReturnSaveUserSuccess() throws Exception{
        when(userAuthenticationService.saveUser(any())).thenReturn(user1);

        mockMvc.perform(post("/register")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(user1)))//firstly, java object will be converted to json string then will  be passed with the mock http request.
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userAuthenticationService,times(1)).saveUser(any());

    }

    @Test
    public void givenUserToSaveReturnSaveUserFailure() throws Exception {
        when(userAuthenticationService.saveUser(any())).thenThrow(UserAlreadyExistsException.class);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user1)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userAuthenticationService,times(1)).saveUser(any());
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            System.out.println("Json Content that has been posted:\n"+jsonContent);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
}

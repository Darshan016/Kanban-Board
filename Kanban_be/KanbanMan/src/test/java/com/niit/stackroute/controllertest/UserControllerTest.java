package com.niit.stackroute.controllertest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.stackroute.config.Producer;
import com.niit.stackroute.controllers.UserController;
import com.niit.stackroute.domain.User;
import com.niit.stackroute.exceptions.UserAlreadyExistsException;
import com.niit.stackroute.services.user.UserService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    private Producer producer;
    private User user,user1;
    List<User> userList;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    public void setUp(){
        user=new User("abc@gmail.com","username","password","12164646","54446464664","1236464f");
//        userList.add(user);
//        userList= Arrays.asList(user);
        mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
    }
    @AfterEach
    public void tearDown(){
        user=null;
    }
    @Test
    public void saveUsersuccess() throws Exception{
        when(userService.saveUser(any())).thenReturn(user);
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());
    }
    @Test
    public void saveUserFailure() throws Exception{
        when(userService.saveUser(any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).saveUser(any());
    }
    @Test
    public void fetchByEmail() throws Exception{
        when(userService.getUser(user.getEmail())).thenReturn(user);
        mockMvc.perform(get("/user/abc@gmail.com").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService,times(1)).getUser(user.getEmail());

    }
    private static String jsonToString(final Object ob)
    {
        String result;

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            System.out.println("Json Content that has been posted:\n"+jsonContent);
            result = jsonContent;

        }
        catch(JsonProcessingException e)
        {
            result = "JSON processing error";
        }

        return result;
    }
}

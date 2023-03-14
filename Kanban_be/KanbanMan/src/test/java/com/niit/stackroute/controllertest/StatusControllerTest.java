package com.niit.stackroute.controllertest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.stackroute.controllers.StatusController;
import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.domain.workspace.Workspace;
import com.niit.stackroute.services.space.SpaceService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)

public class StatusControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private SpaceService spaceService;
    private Status status,status1;
    private Workspace workspace;
    private Task task;
    List<String> members=new ArrayList<>();
    List<Task> taskList=new ArrayList<>();
    List<Status> statusList=new ArrayList<>();
    List<Workspace> workspaceList=new ArrayList<>();
    @InjectMocks
    private StatusController statusController;
    @BeforeEach
    public void setUp(){
        members.add("abc@gmail.com");
        task=new Task("urgent","fe","frontend",new Date(2022-02-12),new Date(2022-03-12),"abc@gmail.com");
        taskList.add(task);
        status=new Status("status1",taskList);
        statusList.add(status);
        workspace=new Workspace("spaceid1","spacename1","abc@gmail.com","email@gmail.com",statusList,members);
        workspaceList.add(workspace);
        mockMvc=MockMvcBuilders.standaloneSetup(statusController).build();
    }
    @AfterEach
    public void tearDown(){
        {
            workspace=null;
        }

    }
    @Test
    public void saveSpaceSucces() throws Exception{
        when(spaceService.saveSpace(any())).thenReturn(workspace);
        mockMvc.perform(post("/api/s1/workspace").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).saveSpace(any());
    }
    @Test
    public void updateSpace() throws Exception{
        when(spaceService.updateWorkspace(any())).thenReturn(workspace);
        mockMvc.perform(post("/api/s1/updatespace").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).updateWorkspace(any());
    }
    @Test
    public void getSpaceByEmailCheck() throws Exception{
        when(spaceService.getByEmail(workspace.getEmail())).thenReturn(workspaceList);
        mockMvc.perform(get("/api/s1/spaces/email@gmail.com").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).getByEmail(any());

    }
    @Test
    public void deleteSpace() throws Exception{
        when(spaceService.deleteSpace(any())).thenReturn(true);
        mockMvc.perform(delete("/api/s1/space/spaceid1").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).deleteSpace(any());
    }
    @Test
    public void fetchSpaceBySpaceIdCheck() throws Exception{
        when(spaceService.getWorkSpaceById(workspace.getSpaceID())).thenReturn(workspace);
        mockMvc.perform(get("/api/s1/space/spaceid1").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).getWorkSpaceById(workspace.getSpaceID());

    }
    @Test
    public void updateWorkSpaceCheck() throws Exception{
        when(spaceService.updateWorkspace(workspace)).thenReturn(workspace);
        mockMvc.perform(post("/api/s1/updatespace").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).updateWorkspace(workspace);
    }
    @Test
    public void deletemembers() throws Exception{
        when(spaceService.deleteGuestMembers(workspace.getSpaceID(),workspace.getEmail())).thenReturn(workspace);
        mockMvc.perform(post("/api/s1/deletememberinworkspace/spaceid1/email@gmail.com").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).deleteGuestMembers(workspace.getSpaceID(),workspace.getEmail());
    }
    @Test
    public void getAllTasks() throws Exception{
        when(spaceService.getAllTaskInStatus(workspace.getSpaceID(),status.getStatusName())).thenReturn(taskList);
        mockMvc.perform(get("/api/s1/getalltaskbyworkspaceId/spaceid1/status1").contentType(MediaType.APPLICATION_JSON).content(jsonToString(workspace))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(spaceService,times(1)).getAllTaskInStatus(workspace.getSpaceID(),status.getStatusName());
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException
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

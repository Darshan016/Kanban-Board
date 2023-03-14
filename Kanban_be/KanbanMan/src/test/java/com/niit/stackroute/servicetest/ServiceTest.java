package com.niit.stackroute.servicetest;

import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.domain.workspace.Workspace;
import com.niit.stackroute.repository.SpaceRepository;
import com.niit.stackroute.services.space.SpaceServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private SpaceRepository spaceRepository;
    @InjectMocks
    private SpaceServiceImpl spaceService;
    private Workspace workspace;
    private Status status,status2;

    private Task task;
    List<Workspace> list=new ArrayList<>();
    List<Status> statusList=new ArrayList<>();
    List<String> members=new ArrayList<>();
    List<Task> taskList=new ArrayList<>();
    @BeforeEach
    public void setUp(){

        members.add("abc@gmail.com");
        Task tasks = new Task("urgent", "be", "needed", new Date(2022 - 05 - 11), new Date(2022 - 06 - 11),"email@gmail.com");

        taskList.add(tasks);
        List<Status> status1=new ArrayList<>();
        //status1.add(tasks);
        status=new Status("nprogress",taskList);
        statusList.add(status);
        workspace=new Workspace("1","space1","descripton","abc@gmail.com",statusList,members);
        status2=new Status("done",taskList);
        list.add(workspace);

    }
    @AfterEach
    public void tearDown(){

        workspace=null;
    }
    @Test
    public void saveCheck(){

        when(spaceRepository.save(workspace)).thenReturn(workspace);
        assertEquals(workspace,spaceService.saveSpace(workspace));
        verify(spaceRepository,times(1)).save(workspace);
    }
    @Test
    public void delete(){

        boolean flag=spaceService.deleteSpace(workspace.getSpaceID());
        assertEquals(true,flag);

        verify(spaceRepository,times(1)).deleteById(any());

    }
    @Test
    public void findAll(){
        when(spaceRepository.findAll()).thenReturn(list);
        assertEquals(list,spaceService.getSpace());
        verify(spaceRepository,times(1)).findAll();

    }
    @Test
    public void getAllStatuses(){

        when(spaceRepository.findById(any())).thenReturn(Optional.ofNullable(workspace));
        assertEquals(statusList,spaceService.getAllStatusInWorkspace(workspace.getSpaceID()));
        verify(spaceRepository,times(1)).findById(any());

    }
    @Test
    public void getSpaceByEmail(){
        when(spaceRepository.findByEmail(workspace.getEmail())).thenReturn(list);
        assertEquals(list,spaceService.getByEmail(workspace.getEmail()));
    }
    @Test
    public void getSpaceBySpaceId(){
//
        when(spaceRepository.findBySpaceID(workspace.getSpaceID())).thenReturn(workspace);
        assertEquals(workspace,spaceService.getWorkSpaceById(workspace.getSpaceID()));
    }
    @Test
    public void updateWorjkSpace(){
        when(spaceRepository.save(workspace)).thenReturn(workspace);
        workspace.setEmail("demo@gmail.com");
        Workspace workspace1=spaceService.updateWorkspace(workspace);
        assertEquals(workspace.getEmail(),workspace1.getEmail());
    }
    @Test
    public void getAllMembers(){
        when(spaceRepository.findBySpaceID(workspace.getSpaceID())).thenReturn(workspace);
        assertEquals(members,spaceService.getAllMembers(workspace.getSpaceID()));
        assertEquals(1,members.size());
    }
    @Test
    public void addNewStatusToWOrkspace(){

        statusList.add(status);

        assertEquals(2,statusList.size());

    }




}

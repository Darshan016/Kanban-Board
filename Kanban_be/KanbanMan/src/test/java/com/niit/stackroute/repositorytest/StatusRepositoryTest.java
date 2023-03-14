package com.niit.stackroute.repositorytest;

import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.domain.workspace.Workspace;
import com.niit.stackroute.repository.SpaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class StatusRepositoryTest {
    @Autowired
    private SpaceRepository spaceRepository;
    private Workspace workspace;
    private Status status,status1;
    private Task task,task1;
    List<String> members=new ArrayList<>();
    List<Task> taskList=new ArrayList<>();
    List<Status> statusList=new ArrayList<>();

    List<Workspace>workspaceList=new ArrayList<>();
    //    private Task task;
    @BeforeEach
    public void setUp(){
        members.add("member1");
        task=new Task("urgent","fe","frontend",new Date(2022-02-12),new Date(2022-03-12),"user@gmail.com");
        taskList.add(task);
        status=new Status("status1",taskList);
        statusList.add(status);
        workspace=new Workspace("spaceid1","spacename1","abc@gmail.com","email@gmail.com",statusList,members);
        workspaceList.add(workspace);
    }
    @AfterEach
    public void tearDown(){
        task=null;
        status=null;
        workspace=null;
    }
    @Test
    public void saveWorkSpace(){
        spaceRepository.save(workspace);
        Workspace workspace1=spaceRepository.findById(workspace.getSpaceID()).get();
        assertNotNull(workspace1);
        assertEquals(workspace.getSpaceID(),workspace1.getSpaceID());
    }
    @Test
    public void deleteWorkSpace(){
        Workspace workspace1=spaceRepository.findById(workspace.getSpaceID()).get();
        spaceRepository.delete(workspace1);
        assertEquals(Optional.empty(),spaceRepository.findById(workspace.getSpaceID()));
    }
//    @Test
//    public void fetchAll(){
//        Workspace workspace1=new Workspace("2","name2","xyz@gmail.com","email@gmail.com",statusList,members);
//        List<Workspace>workspaces=spaceRepository.findAll();
//        workspaces.add(workspace1);
//        assertEquals(3,workspaces.size());
//    }
    @Test
    public void fetchSpaceBySpaceIdCheck(){
        spaceRepository.save(workspace);
        Workspace workspace1= spaceRepository.findBySpaceID(workspace.getSpaceID());
        assertNotNull(workspace1);
        assertEquals(workspace.getSpaceID(),workspace1.getSpaceID());

    }
    @Test
    public void getSpaceByEmailCheck(){
        spaceRepository.save(workspace);
        List<Workspace> workspace1=spaceRepository.findByEmail(workspace.getEmail());
        assertNotNull(workspace1);
        assertEquals(1,workspace1.size());
    }



}

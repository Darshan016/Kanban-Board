package com.niit.stackroute.services.space;

import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.domain.workspace.Workspace;

import java.util.List;

public interface SpaceService {


    //workspace methods

    Workspace saveSpace(Workspace workspace);

    List<Workspace> getSpace();

    Workspace updateWorkspace(Workspace workspace);

    boolean deleteSpace(String spaceId);

    List<Workspace> getByEmail(String email);

    Workspace getWorkSpaceById(String SpaceId);


    //status

    Workspace addNewStatusToWorkspace(String spaceId, Status status);

    Workspace removeStatusFromWorkspace(String spaceId, Status status);

    Workspace updateStatusToWorkspace(String spaceId, Status status);

    List<Status> getAllStatusInWorkspace(String spaceId);


    //task

    Workspace addNewTaskToStatus(String spaceId, String StatusName, Task task);

    public Workspace removeTaskFromStatus(String spaceId, String StatusName, Task task);

    List<Task> getAllTaskInStatus(String spaceId, String statusName);

//    Workspace updateTaskToStatus(String spaceId, String statusName, Task task);
Workspace deleteGuestMembers(String spaceId, String emailId);

    Workspace updateTaskToStatus(String spaceId, String statusName,String taskName, Task task);


    //member

    List<Workspace> getByGuest(String emailId);

    Workspace addGuestMembers(String spaceId ,String emailId);

    List<String> getAllMembers(String SpaceId);

}

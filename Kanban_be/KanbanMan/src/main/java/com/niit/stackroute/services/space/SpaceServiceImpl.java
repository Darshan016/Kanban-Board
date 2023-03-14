package com.niit.stackroute.services.space;

import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.domain.workspace.Workspace;
import com.niit.stackroute.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService {
    private final SpaceRepository spaceRepository;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }


    //space


    @Override
    public Workspace saveSpace(Workspace workspace) {
        return spaceRepository.save(workspace);
    }

    @Override
    public List<Workspace> getSpace() {
        return spaceRepository.findAll();
    }

    @Override
    public Workspace updateWorkspace(Workspace workspace) {
        return spaceRepository.save(workspace);
    }

    @Override
    public boolean deleteSpace(String spaceId) {
        spaceRepository.deleteById(spaceId);
        return true;
    }

    @Override
    public List<Workspace> getByEmail(String email) {
        return spaceRepository.findByEmail(email);
    }

    @Override
    public Workspace getWorkSpaceById(String SpaceId) {

        return spaceRepository.findBySpaceID(SpaceId);
    }


    // Status


    @Override
    public Workspace addNewStatusToWorkspace(String spaceId, Status status) {
        if (spaceRepository.findById(spaceId).isPresent()) {
            Workspace workspace = spaceRepository.findById(spaceId).get();

//            if(workspace.getStatus().isEmpty())
//            {
//
//                workspace.addStatus(status);
//                return spaceRepository.save(workspace);
//            }
//            for (Status s : workspace.getStatus()) {
//                System.out.println("Status Available"+s);
//                if (s.getStatusName().equals(status.getStatusName())) {
//                    return null;
//                }
//
//            }
            workspace.addStatus(status);
            return spaceRepository.save(workspace);

        }
        return null;

    }

//    @Override
//    public Workspace addNewStatusToWorkspace(String spaceId, Status status) {
//        if(spaceRepository.findById(spaceId).isPresent()) {
//            Workspace workspace = spaceRepository.findById(spaceId).get();
//
//            if(workspace.getStatus()==null)
//            {
//
//                workspace.addStatus(status);
//                return spaceRepository.save(workspace);
//            }
//            for (Status s : workspace.getStatus()) {
//                System.out.println("Status Available"+s);
//                if (s.getStatusName().equalsIgnoreCase(status.getStatusName())) {
//                    return null;
//                }
//
//            }
//            workspace.addStatus(status);
//            return spaceRepository.save(workspace);
//
//        }
//        return null;
//
//    }

//    @Override
//    public Workspace removeStatusFromWorkspace(String spaceId, Status status) {
//        Workspace workspace = spaceRepository.findById(spaceId).get();
//        workspace.getStatus().remove(status);
//        return spaceRepository.save(workspace);
//    }

    @Override
    public Workspace removeStatusFromWorkspace(String spaceId, Status status) {
        Workspace workspace=spaceRepository.findById(spaceId).get();
        for(Status s:workspace.getStatus()){
            if(s.getStatusName().equals(status.getStatusName())){
                System.out.println("For S "+s);
                workspace.getStatus().remove(status);
                return spaceRepository.save(workspace);
            }

        }
        return null;

    }

    @Override
    public Workspace updateStatusToWorkspace(String spaceId, Status status) {
        Workspace workspace = spaceRepository.findById(spaceId).get();
        List<Status> statusList = workspace.getStatus();
        Status myStatus = null;
        for (Status status1 : statusList) {
            if (status1.getStatusName().equals(status.getStatusName())) {
                myStatus = status1;
                break;
            }
        }
        statusList.remove(myStatus);
        statusList.add(status);
        workspace.setStatus(statusList);
        return spaceRepository.save(workspace);
    }


    @Override
    public List<Status> getAllStatusInWorkspace(String spaceId) {
        List<Status> status = new ArrayList<>();
        Workspace kanbanDetails = spaceRepository.findById(spaceId).get();
        status = kanbanDetails.getStatus();
        return status;
    }

    @Override
    public Workspace addNewTaskToStatus(String spaceId, String StatusName, Task task) {
        Workspace workspace = spaceRepository.findById(spaceId).get();
        List<Status> status = workspace.getStatus();
        int count=0;
        for (Status status1 : status) {
            if (status1.getStatusName().equals(StatusName)) {
                if(status1.getTask()!=null){
                for(Task task1:status1.getTask()) {

                    if (task1.getAssignTo().equals(task.getAssignTo())) {

                        count++;
                        if (count ==3) {

                            return null;
                        }
                    }
                }
                }
             if(status1.getTask()==null){
                 status1.setTask(new ArrayList<>());
                 status1.addTask(task);
             }else{
                 status1.addTask(task);
             }


            }
        }
        return spaceRepository.save(workspace);
    }


    @Override
    public Workspace removeTaskFromStatus(String spaceId, String StatusName, Task task) {
        Workspace workspace = spaceRepository.findById(spaceId).get();
        List<Status> status = workspace.getStatus();
        for (Status status1 : status) {
            if (status1.getStatusName().equals(StatusName)) {
                status1.getTask().remove(task);
            }
        }

        return spaceRepository.save(workspace);
    }

    @Override
    public List<Task> getAllTaskInStatus(String spaceId, String statusName) {
        List<Status> status1 = new ArrayList<>();
        Workspace workspace = spaceRepository.findById(spaceId).get();
        status1 = workspace.getStatus();
        List<Task> tasks = new ArrayList<>();
        for (Status status2 : status1)
            if (status2.getStatusName().equals(statusName)) tasks = status2.getTask();
        System.out.println(tasks);
        return tasks;
    }

//    @Override
//    public Workspace updateTaskToStatus(String spaceId, String statusName, Task task) {
//        Workspace kanbanDetails = spaceRepository.findById(spaceId).get();
//        List<Status> statusList = kanbanDetails.getStatus();
//        Status myStatus = null;
//        Status status = new Status();
//        for (Status status1 : statusList) {
//            if (status1.getStatusName().equals(statusName)) {
//                myStatus = status1;
//                Task myTask = null;
//
//                List<Task> tasks = status1.getTask();
//                for (Task task1 : tasks) {
//                    if (task1.getTaskName().equals(task.getTaskName())) {
//                        myTask = task1;
//                    }
//                }
//                tasks.remove(myTask);
//                tasks.add(task);
//                // status.setStatusName(myStatus.getStatusName());
//                status.setStatusName(myStatus.getStatusName());
//                status.setTask(tasks);
//                statusList.remove(status1);
//                statusList.add(status);
//                break;
//            }
//        }
//
//
//        kanbanDetails.setStatus(statusList);
//        return spaceRepository.save(kanbanDetails);
//    }

    @Override
    public List<Workspace> getByGuest(String emailId) {
        List<Workspace> workspaces = spaceRepository.findAll();
        List<Workspace> list = new ArrayList<>();

        for (Workspace details : workspaces) {
            if (details.getMembers() != null) {
                List<String> strings = details.getMembers();
                for (String s : strings) {
                    if (s.equals(emailId)) {
                        list.add(details);
                    }
                }

            }

        }
        return list;
    }
//
//    @Override
//    public Workspace addGuestMembers(String spaceId, String emailId) {
//        Workspace kanbanDetails = spaceRepository.findById(spaceId).get();
//        kanbanDetails.addEmail(emailId);
//        return kanbanDetails;
//    }


    @Override
    public Workspace addGuestMembers(String spaceId, String emailId) {
        Workspace kanbanDetails=spaceRepository.findById(spaceId).get();
        if(kanbanDetails.getMembers()==null){
            kanbanDetails.setMembers(new ArrayList<>());
            kanbanDetails.getMembers().add(emailId);
            return spaceRepository.save(kanbanDetails);
        }
        else{

            kanbanDetails.getMembers().add(emailId);
            return spaceRepository.save(kanbanDetails);
        }

    }

    @Override
    public List<String> getAllMembers(String SpaceId) {
        Workspace workspace = spaceRepository.findBySpaceID(SpaceId);

        if (!workspace.getMembers().isEmpty()) {
            return new ArrayList<>(workspace.getMembers());
        }
        return null;
    }
    @Override
    public Workspace updateTaskToStatus(String spaceId, String statusName,String taskName, Task task) {
        Workspace workspace=spaceRepository.findById(spaceId).get();
        List<Status> statusList=workspace.getStatus();

        Task task1=null;
        for(Status status:statusList){
            if(status.getStatusName().equals(statusName)){
                for(Task t:status.getTask()){
                    if(t.getTaskName().equals(taskName))
                    {
                        task1=t;
                        System.out.println("T"+task1);
                        status.getTask().remove(task1);
                        status.getTask().add(task);
                        return spaceRepository.save(workspace);
                    }
                }
            }
        }
        return spaceRepository.save(workspace);
    }

    @Override
    public Workspace deleteGuestMembers(String spaceId, String emailId) {
        Workspace kanbanDetails=spaceRepository.findById(spaceId).get();

        for(String me:kanbanDetails.getMembers()){


            if(me.equals(emailId)) {
                System.out.println("Inside if " + me + emailId);
                kanbanDetails.getMembers().remove(me);
                return spaceRepository.save(kanbanDetails);
            }
            if(kanbanDetails.getMembers()==null){
                return null;
            }



        }

        return kanbanDetails;
    }
}

package com.niit.stackroute.controllers;

import com.niit.stackroute.config.EmailDetails;
import com.niit.stackroute.config.Producer;
import com.niit.stackroute.domain.workspace.Workspace;
import com.niit.stackroute.domain.workspace.Status;
import com.niit.stackroute.domain.workspace.Task;
import com.niit.stackroute.services.space.SpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin
@RequestMapping("/api/s1/")
public class StatusController {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Producer producer;

    private ResponseEntity<?> responseEntity;
    private final SpaceService spaceService;

    @Autowired
    public StatusController( SpaceService spaceService) {

        this.spaceService = spaceService;
    }

    //workspace CRUD operations
    //---------------------------------------------------------------------------------------------------------
//save,getAll,deletebyId,update,getByEmail

    //http://localhost:9000/api/s1/workspace
    @PostMapping("workspace")
  public ResponseEntity<?> saveSpace(@RequestBody Workspace workspace)  {
        responseEntity = new ResponseEntity<>(spaceService.saveSpace(workspace), HttpStatus.CREATED);
        return responseEntity;
    }

    //http://localhost:9000/api/s1/spaces
    @GetMapping("spaces")
    public ResponseEntity<?> getAllSpaces() {
        responseEntity = new ResponseEntity<>(spaceService.getSpace(), HttpStatus.OK);
        return responseEntity;
    }
    //http://localhost:9000/api/s1/space/{xxxx}
    @DeleteMapping("space/{spaceId}")
    public ResponseEntity<?> deleteSpaceById(@PathVariable String spaceId) {
        responseEntity = new ResponseEntity<>(spaceService.deleteSpace(spaceId), HttpStatus.OK);
        return responseEntity;
    }
    //http://localhost:9000/api/s1/updatespace
    @PostMapping("/updatespace")
    public ResponseEntity<?> updateWorkspace(  @RequestBody Workspace workspace){
        return new ResponseEntity<>(spaceService.updateWorkspace(workspace),HttpStatus.OK);
    }




    //http://localhost:9000/api/s1/spaces/{email}
    @GetMapping("spaces/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return new ResponseEntity<>(spaceService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("space/{spaceId}")
    public ResponseEntity<?> getBySpaceId(@PathVariable String spaceId) {
        return new ResponseEntity<>(spaceService.getWorkSpaceById(spaceId), HttpStatus.OK);
    }

    //----------------------------------------------------------------------------------------------------

    //Status CRUD operations

    //http://localhost:9000/api/s1/addnewstatusintoworkspace/{workspaceId}
    @PostMapping("/addnewstatusintoworkspace/{workspaceId}")
    public ResponseEntity<?> AddNewStatus(@PathVariable String workspaceId,@RequestBody Status status){
        return new ResponseEntity<>(spaceService.addNewStatusToWorkspace(workspaceId,status),HttpStatus.OK);
    }






    //http://localhost:9000/api/s1/deletestatusintoworkspace/{workspaceId}
    @PostMapping("/deletestatusintoworkspace/{workspaceId}")
    public ResponseEntity<?> deleteStatus(@PathVariable String workspaceId,@RequestBody Status status){
        return new ResponseEntity<>(spaceService.removeStatusFromWorkspace(workspaceId,status),HttpStatus.OK);
    }
    //http://localhost:9000/api/s1/updatestatusintoworkspace/{workspaceId}
    @PostMapping("/updatestatusintoworkspace/{workspaceId}")
    public ResponseEntity<?> UpdateStatus(@PathVariable String workspaceId,@RequestBody Status status){
        return new ResponseEntity<>(spaceService.updateStatusToWorkspace(workspaceId,status),HttpStatus.OK);
    }
    //http://localhost:9000/api/s1/getallstatusbyworkspaceId/{workspaceId}
    @GetMapping("/getallstatusbyworkspaceId/{workspaceId}")
    public ResponseEntity<?> GetStatusByKanbanId( @PathVariable String workspaceId){
        return new ResponseEntity<>(spaceService.getAllStatusInWorkspace(workspaceId),HttpStatus.OK);
    }

    //Task CRUD operations

    //-------------------------------------------------------------------------------------------------------------------------------------

    //http://localhost:9000/api/s1/addnewtaskintostatus/{workspaceId}/{StatusId}
    @PostMapping("/addnewtaskintostatus/{workspaceId}/{StatusName}")
    public ResponseEntity<?> AddNewTask(@PathVariable String workspaceId,@PathVariable String StatusName,@RequestBody Task task) {
        Workspace space = spaceService.addNewTaskToStatus(workspaceId, StatusName, task);
        if (space != null) {
            if (task.getAssignTo() != null) {
                EmailDetails emailDetails = new EmailDetails(task.getAssignTo(), "", "", "");
                producer.sendTaskMail(emailDetails);
            }
            return new ResponseEntity<>(space, HttpStatus.OK);
        }
        return null;
    }
    //http://localhost:9000/api/s1/deletetaskintostatus/{workspaceId}/{StatusId}
    @PostMapping("/deletetaskintostatus/{workspaceId}/{StatusId}")
    public ResponseEntity<?> DeleteNewTask(@PathVariable String workspaceId,@PathVariable String StatusId,@RequestBody Task task){
        return new ResponseEntity<>(spaceService.removeTaskFromStatus(workspaceId,StatusId,task),HttpStatus.OK);
    }

    //http://localhost:9000/api/s1/getalltaskbyworkspaceId/{workspaceId}/{statusId}
    @GetMapping("/getalltaskbyworkspaceId/{workspaceId}/{statusId}")
    public ResponseEntity<?> GetStatusByKanbanId( @PathVariable String workspaceId,@PathVariable String statusId){
        return new ResponseEntity<>(spaceService.getAllTaskInStatus(workspaceId,statusId),HttpStatus.OK);
    }


    //http://localhost:9000/api/s1/updatetaskintostatus/{workspaceId}/{StatusId}
//    @PostMapping("/updatetaskintostatus/{workspaceId}/{StatusId}")
//    public ResponseEntity<?> UpdateNewTask(@PathVariable String workspaceId,@PathVariable String StatusId,@RequestBody Task task){
//        return new ResponseEntity<>(spaceService.updateTaskToStatus(workspaceId,StatusId,task),HttpStatus.OK);
//    }



    @PostMapping("/updatetaskintostatus/{workspaceId}/{statusName}/{taskName}")
    public ResponseEntity<?> UpdateNewTask(@PathVariable String workspaceId,@PathVariable String statusName,@PathVariable String taskName,@RequestBody Task task){
        return new ResponseEntity<>(spaceService.updateTaskToStatus(workspaceId,statusName,taskName,task),HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //ADDING AND GET MEMBERS

    //http://localhost:9000/api/s1/getkanbanbymember/{emailId}
    @GetMapping("/getkanbanbymember/{emailId}")
    public ResponseEntity<?> GetKanbanByMembers( @PathVariable String emailId){
        return new ResponseEntity<>(spaceService.getByGuest(emailId),HttpStatus.OK);
    }


    //http://localhost:9000/api/s1/addmemberinworkspace/{workspaceId}/{emailId}
    @PostMapping("/addmemberinworkspace/{workspaceId}/{emailId}")
    public ResponseEntity<?> AddMembersInKanban(@PathVariable String workspaceId, @PathVariable String emailId){
        EmailDetails emailDetails=new EmailDetails(emailId,"","You Have Been Added as a member for this project","Added");
        producer.sendMemberToRabbitMq(emailDetails);
        return new ResponseEntity<>(spaceService.addGuestMembers(workspaceId,emailId),HttpStatus.OK);
    }

    //http://localhost:9000/api/s1/getmembersinworkspace/{workspaceId}
    @GetMapping("/getmembersinworkspace/{workspaceId}")
    public ResponseEntity<?> getMembersInKanban(@PathVariable String workspaceId){
        return new ResponseEntity<>(spaceService.getAllMembers(workspaceId),HttpStatus.OK);
    }

    @PostMapping("/deletememberinworkspace/{workspaceId}/{emailId}")
    public ResponseEntity<?> deleteMembersInKanban(@PathVariable String workspaceId, @PathVariable String emailId){
        return new ResponseEntity<>(spaceService.deleteGuestMembers(workspaceId,emailId),HttpStatus.OK);
    }
}
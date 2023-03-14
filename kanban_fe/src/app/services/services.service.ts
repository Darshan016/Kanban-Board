import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ServicesService {

  

  constructor(private httpClient : HttpClient, private router:Router) { }
  isLoggedIn: boolean=false;
  
  
  email:any;
  userName:any;
  space:any;
  updatedTask:any;
  status:any;
  statusData:any;
  List:any;
  spaceId:any;
  x:number=0;
  profileC:boolean=false;
  
  registerUser(user:any)
  {
    this.httpClient.post('http://localhost:8082/register',user).subscribe(data=>{
      console.log(data);
      alert("Registered Successfully")
      this.router.navigate(['login'])
    },
    error=>{console.log(error);
    })
  }


  loginUser(data1:any)
  {
    return this.httpClient.post('http://localhost:8082/login',data1)
    
  }


  getSpaceByEmail()
  {
    return this.httpClient.get(`http://localhost:8082/api/s1/spaces/${this.email}`)
  }

  getSpaceByEmailAssigned()
  {
    return this.httpClient.get(`http://localhost:8082/api/s1/getkanbanbymember/${this.email}`)
  }

  
  saveSpace(data:any)
  {
    console.log(data);
    
    return this.httpClient.post("http://localhost:8082/api/s1/workspace",data);
  }

  deleteSpace(spaceId:any)
  {
    console.log(spaceId);
  
    // http://localhost:9000/api/s1/space/{xxxx}
    return this.httpClient.delete(`http://localhost:8082/api/s1/space/${spaceId}`);
  }

  updateSpace(space:any){
    
    return this.httpClient.post(`http://localhost:8082/api/s1/updatespace`,space);
  }


  getSpace(spaceId:any)
  {
    console.log(spaceId);
  
    
    return this.httpClient.get(`http://localhost:8082/api/s1/space/${spaceId}`);
  }


  //Status services
  getstatus(spaceId:any)
  {
    console.log(spaceId);
   
    return this.httpClient.get(`http://localhost:8082/api/s1/getallstatusbyworkspaceId/${spaceId}`);
  }
  getstatusBySpace(spaceName:any)
  {
    console.log(spaceName);
   
    return this.httpClient.get(`http://localhost:8082/api/s1/status/${spaceName}`);
  }
  getTaskBySpace(workspaceId:any,statusId:any)
  {
    console.log("Please help me|||");
   
    return this.httpClient.get(`http://localhost:8082/api/s1/getalltaskbyworkspaceId/${workspaceId}/${statusId}`);
  }

  saveStatus(workspaceId:any,status:any)
  {
    return this.httpClient.post(`http://localhost:8082/api/s1/addnewstatusintoworkspace/${workspaceId}`,status)
  }

  // http://localhost:9000/api/s1/deletestatusintoworkspace/{workspaceId}

  deleteStatus(workspaceId:any,data:any)
  {
    return this.httpClient.post(`http://localhost:8082/api/s1/deletestatusintoworkspace/${workspaceId}`,data)
  }


  getTasks(data:any)
  {
    console.log(data);
    
    return this.httpClient.get(`http://localhost:8082/api/s1/task/${data.email}/${data.spaceName}`)
  }

  addTask(workspaceId:any,StatusId:any,data:any)
  {
    return this.httpClient.post(`http://localhost:8082/api/s1/addnewtaskintostatus/${workspaceId}/${StatusId}`,data);
  }
//http://localhost:9000/api/s1/addnewtaskintostatus/{workspaceId}/{StatusId}
 



addMember(workspaceId:any,member:any)
{
  return this.httpClient.post(`http://localhost:8082/api/s1/addmemberinworkspace/${workspaceId}/${member}`,"");
}




getAllMembers(workspaceId:any)
{
  return this.httpClient.get(`http://localhost:8082/api/s1/getmembersinworkspace/${workspaceId}`);
}
  getTaskByEmail()
  {
    console.log("email");
    
    console.log(this.email);
    
    return this.httpClient.get(`http://localhost:9000/api/s1/task/${this.email}`);
  }

  getNotificationById(id:any)
  {
    return this.httpClient.get(`http://localhost:9000/api/s1/notifications/${id}`);
  }

  getUserByEmail()
  {
    return this.httpClient.get(`http://localhost:9000/user/${this.email}`);
  }

  getTaskByEnailAndSpace(UserEmail:any,spaceName:any)
  {
    return this.httpClient.get(`http://localhost:9000/api/s1/task/${UserEmail}/${spaceName}`);
  }

  updateTaskByStatus(workspaceId:any,StatusName:any,taskName:any,data:any)
  {
    console.log("in service");
    console.log(data);
    
    
    return this.httpClient.post(`http://localhost:8082/api/s1/updatetaskintostatus/${workspaceId}/${StatusName}/${taskName}`,data);
  }

  deleteTaskByStatus(workspaceId:any,StatusId:any,data:any)
  {
    return this.httpClient.post(`http://localhost:8082/api/s1/deletetaskintostatus/${workspaceId}/${StatusId}`,data);
  }






  sendEmailNotification(count:any)
  {

    
    return this.httpClient.post(`http://localhost:8082/api/s1//sendEmail/${this.email}`,count);
  }



  deleteMembers(workspaceId:any,member:any)
{
  return this.httpClient.post(`http://localhost:8082/api/s1/deletememberinworkspace/${workspaceId}/${member}`,"");
}

updateStatus(workspaceId:any,priviousStatus:any,data:any){
  return this.httpClient.post(`http://localhost:8082/api/s1/updatestatusintoworkspace/${workspaceId}/${priviousStatus}`,data);

}

}

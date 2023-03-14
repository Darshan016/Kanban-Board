import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ServicesService } from '../services/services.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-workspace-dashboard',
  templateUrl: './workspace-dashboard.component.html',
  styleUrls: ['./workspace-dashboard.component.css']
})
export class WorkspaceDashboardComponent {
spaceId:any;
currentSpace:any;
userName:any;
Swal:any=Swal;


status: any = [];
newStatus: any = {};
num: number = 1;

tasks: any = {};
newTask: any = {};
oldTask: any = {};
viewTask: number = 0;
number: number = 0;
count:number=0;



statusBySpace: any = [];
taskCount: any = 0;
noOfTasks: any = [];
limitingCount: any = 4;
totalCount: any = 0;
step!: number;
ArrayOfList: any = [];
taskObject: any = {};
dummytask: any;
currentItemsToShow: any;
currentDate=new Date();
weeks :any= [];
connectedTo:any = [];


members:any={};


n:number=0;



	constructor(private router: Router,private services: ServicesService) {

this.n=this.services.x;
  }




	ngOnInit() {
    this.userName=this.services.userName;
    this.spaceId=this.services.spaceId;
    this.services.getSpace(this.spaceId).subscribe(data => {
      this.currentSpace=data;

      console.log(data);
    }, error => {
      console.log(error);

    })



    this.services.getstatus(this.spaceId).subscribe(data => {
      this.status=data;
      for (let week of this.status) {
        this.connectedTo.push(week.statusName);
            };
      console.log(this.connectedTo);

      console.log(data);
    }, error => {
      console.log(error);

    })
    this.services.getAllMembers(this.spaceId).subscribe(data => {
      this.members=data;

      console.log(data);
    }, error => {
      console.log(error);

    })

  }




  createStatus()
  {

    this.num=2;
  }
  cancelMethod()
  {
    this.num = 1;
    this.ngOnInit();
  }



  sf=new FormGroup({
    statusName:new FormControl('',Validators.required),
  })

  getstatusName() { return this.sf.controls['statusName'];}

  saveStatus(){
 this.services.saveStatus(this.spaceId,this.sf.value).subscribe(data=>{
  this.currentSpace=data;
  console.log(data);
  this.num=1;
  this.ngOnInit();
 }, error => {
  console.log(error);

})

  }

delete(a:any)
{


  const swalWithBootstrapButtons = this.Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: false
  })

  swalWithBootstrapButtons.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes, delete it!',
    cancelButtonText: 'No, cancel!',
    reverseButtons: true
  }).then((result:any) => {
    if (result.isConfirmed) {
      swalWithBootstrapButtons.fire(
        'Deleted!',
        'Status Deleted.',
        'success'
      )

      this.services.deleteStatus(this.spaceId,a).subscribe(data=>{
        // this.currentSpace=data;
        console.log(data);
        // this.num=1;
        this.ngOnInit();
       }, error => {
        console.log(error);

      })

    } else if (
      /* Read more about handling dismissals below */
      result.dismiss === this.Swal.DismissReason.cancel
    ) {
      swalWithBootstrapButtons.fire(
        'Cancelled',
        'Operation Cancelled.',
        'error'
      )
    }
  })






}





  drop2(event: CdkDragDrop<string[]>) {
    if(event.container.data==null){
      event.container.data==event.previousContainer.data;
    }
    if (event.previousContainer === event.container) {

      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {

      console.log("inside else");
      let a=event.previousContainer.id;
      let b=event.container.id;
      let c=event.previousContainer.data[event.previousIndex];
      let d=event.container.data[event.currentIndex];
       console.log(a+" "+b+" "+" "+c+" "+d);

      this.services.addTask(this.spaceId,b,c).subscribe(response=>{
        this.ngOnInit();
        console.log(response)

        this.services.deleteTaskByStatus(this.spaceId,a,c).subscribe(response=>{
          this.ngOnInit();
          console.log(response)
        }, error => {
          console.log(error);

        });



      }, error => {
        console.log(error);

      });













      transferArrayItem(event.previousContainer.data,
                        event.container.data,
                        event.previousIndex,
                        event.currentIndex);
    }
  }



  showTask:boolean=false;
  currentStatusName:any;
  task:any={};
  change(a:any){
    this.currentStatusName=a;
    this.showTask=!this.showTask;
    console.log(this.showTask)
  }

  addTask(){
    this.services.addTask(this.spaceId,this.currentStatusName,this.task).subscribe(response=>{
      this.ngOnInit();
      this.showTask=!this.showTask;
      console.log(response)
    }, error => {
      console.log(error);

    });


  }






  showMember:boolean=false;
  // currentStatusName:any;
  memberMail?:string;
   showMemberD(){
    this.showMember=!this.showMember;
    console.log(this.showMember)
  }

  addMember(){
    this.services.addMember(this.spaceId,this.memberMail).subscribe(response=>{
      this.ngOnInit();
      this.showMember=!this.showMember;
      console.log(response)
    }, error => {
      console.log(error);

    });


  }



  addMembers(){




    this.Swal.fire({
      title: 'Add Members',
      html: `<input type="text" id="login" class="swal2-input" placeholder="Enter Member Email">`,
      confirmButtonText: 'Do it !!!',
      focusConfirm: false,
      preConfirm: () => {
        var d = this.Swal.getPopup().querySelector('#login').value


        if (d==="") {
          Swal.showValidationMessage(`Please enter Member name`)
        }
        if(d!==""){

          this.services.addMember(this.spaceId,d).subscribe(response=>{
            this.ngOnInit();
            console.log(response)
          }, error => {
            console.log(error);

          });
        }


         return { Current : d}
       }
    }).then((result:any) => {
      this.Swal.fire(`
      Previous Name: ${result.value.Privious}
      Current Name: ${result.value.Current}
      `.trim())
      })


  }








  deleteMember(a:any){
    this.services.deleteMembers(this.spaceId,a).subscribe(response=>{
      this.ngOnInit();
      console.log(response)
    }, error => {
      console.log(error);

    });
  }



  updateTask:boolean=false;
  // currentStatusName:any;
  cStatusName?:string;
  cTaskName?:string;
   showUpdateTask(a:any,b:any){
    this.cStatusName=a;
    this.cTaskName=b;
    this.updateTask=!this.updateTask;
    console.log(this.updateTask)
  }

  updateTasks(){
    this.services.updateTaskByStatus(this.spaceId,this.cStatusName,this.cTaskName,this.task).subscribe(response=>{
      this.ngOnInit();
      this.updateTask=!this.updateTask;
      console.log(response)
    }, error => {
      console.log(error);

    });


  }
deleteTask(a:any,b:any){
  this.services.deleteTaskByStatus(this.spaceId,a,b).subscribe(response=>{
    this.ngOnInit();
    // this.updateTask=!this.updateTask;
    console.log(response)
  }, error => {
    console.log(error);

  });
}


renam(a:any){
  this.Swal.fire({
    title: 'Rename Status Name',
    html: `<input type="text" id="login" class="swal2-input" placeholder="Change Name">`,
    confirmButtonText: 'Do it !!!',
    focusConfirm: false,
    preConfirm: () => {
      var d = this.Swal.getPopup().querySelector('#login').value
      const c=a.statusName;


      if (d==="") {
        Swal.showValidationMessage(`Please enter status name`)
      }
      if(d!==""){

        a.statusName=d;
        this.services.updateStatus(this.spaceId,c,a).subscribe(response=>{
          this.ngOnInit();
          console.log(response)
        }, error => {
          console.log(error);

        });
      }


       return { Privious: c ,Current : d}
     }
  }).then((result:any) => {
    this.Swal.fire(`
    Previous Name: ${result.value.Privious}
    Current Name: ${result.value.Current}
    `.trim())
    })

}





addT(a:any){
  this.Swal.fire({
    title: 'Add Task',
    html: ` <div >

      <input  id="taskName" name="taskName" type="text" class="swal2-input"
         placeholder="Task Name" required maxlength="50" />



      <input id="taskDiscription" name="taskDiscription" type="text" class="swal2-input"
         placeholder="Task Description" maxlength="100" />


 <div style="display: flex; justify-content: space-evenly;">
 <label ><b>Priority</b></label>
 <input type="radio" id="high" name="priority" value="https://images.unsplash.com/photo-1597773150796-e5c14ebecbf5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YWJzdHJhY3QlMjBibHVlJTIwYmFja2dyb3VuZHxlbnwwfHwwfHw%3D&w=1000&q=80">
 <label for="html">High</label><br>
 <input type="radio" id="low" name="priority" value="https://i.pinimg.com/originals/6a/e4/0b/6ae40b17c01bf102aca03dce775c85d6.jpg">
 <label for="css">low</label><br>
 <input type="radio" id="medium" name="priority" value="https://img.freepik.com/free-vector/green-abstract-halftone-background_1409-898.jpg">
 <label for="javascript">medium</label>
 </div>

 <input type="date"  id="date" class="swal2-input" name="date">

  </div>
    `
    ,
    confirmButtonText: 'Do it !!!',
    focusConfirm: false,
    preConfirm: () => {
      var taskName = this.Swal.getPopup().querySelector('#taskName').value
      var taskDiscription = this.Swal.getPopup().querySelector('#taskDiscription').value
      var date = this.Swal.getPopup().querySelector('#date').value
      var priority;
      if(this.Swal.getPopup().querySelector('#high').checked)
      {
        priority= this.Swal.getPopup().querySelector('#high').value

      }

      if(this.Swal.getPopup().querySelector('#low').checked)
      {
        priority= this.Swal.getPopup().querySelector('#low').value

      }

      if(this.Swal.getPopup().querySelector('#medium').checked)
      {
        priority= this.Swal.getPopup().querySelector('#medium').value

      }
      const today = new Date();
      const yyyy = today.getFullYear();
      let mm:any = today.getMonth() + 1; // Months start at 0!
      let dd:any = today.getDate();

      if (dd < 10) dd = '0' + dd;
      if (mm < 10) mm = '0' + mm;

      const formattedToday = yyyy + '-' + mm + '-' + dd;

      this.task.taskName=taskName;
      this.task.taskDescription=taskDiscription;
      this.task.priority=priority;
      this.task.endDate=date;
      this.task.startDate=formattedToday;
      this.currentStatusName=a;


      this.services.addTask(this.spaceId,this.currentStatusName,this.task).subscribe(response=>{
        this.ngOnInit();
        this.showTask=!this.showTask;
        console.log(response)
      }, error => {
        console.log(error);

      });
       return { priority:  priority,taskName : taskName,taskDiscription: taskDiscription,date:date,formattedToday:formattedToday}
     }
  }).then((result:any) => {
    this.Swal.fire(`
    priority Name: ${result.value.priority}
    taskName Name: ${result.value.taskName}
    taskDiscription Name: ${result.value.taskDiscription}
    date Name: ${result.value.date}
    formattedToday Name: ${result.value.formattedToday}
    `.trim())
    })

}}

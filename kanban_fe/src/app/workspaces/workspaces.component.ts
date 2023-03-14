import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ServicesService } from '../services/services.service';
import { UpdateWorkSpaceComponent } from '../update-work-space/update-work-space.component';

@Component({
  selector: 'app-workspaces',
  templateUrl: './workspaces.component.html',
  styleUrls: ['./workspaces.component.css']
})
export class WorkspacesComponent {

  spaces: any;
  space: any = {};

  guestspaces:any;

  userName:any;
  ArrayOfList: any;

  constructor( private services: ServicesService, private router: Router,public dialog: MatDialog) {
    this.addSp.email=this.services.email;
    this.services.x=0;
   }
  ngOnInit() 
  { 
    this.userName = this.services.userName;
    this.services.getSpaceByEmail().subscribe(data => {
      this.spaces = data;

    },
      error => {
        console.log(error);
      });


      
      this.services.getSpaceByEmailAssigned().subscribe(data => {
        this.guestspaces = data;
  
      },
        error => {
          console.log(error);
        });
  }

updatedValue:any={};
  updateShow(data:any)
  {
    this.updatedValue=data;
    this.showUpdate=!this.showUpdate;
    console.log(this.showUpdate)
  }
  
  updateSpace(data: any) 
  {
    this.services.updateSpace(data).subscribe(response=>{
      this.ngOnInit();
      this.showUpdate=false;
      console.log(response)
    }, error => {
      console.log(error);

    });
  }

  user(s:any){
this.services.spaceId=s;
 this.router.navigateByUrl("/dash");

  }


  userX(s:any){
    this.services.spaceId=s;
this.services.x=2;
     this.router.navigateByUrl("/dash");
    
      }


show:boolean=false;
showUpdate:boolean=false;
  change(){
    this.show=!this.show;
    console.log(this.show)
  }
addSp:any={};


  addSpace(data:any)
  {
    this.services.saveSpace(data).subscribe(response=>{
      this.ngOnInit();
      this.show=false;
      console.log(response)
    }, error => {
      console.log(error);

    });
  }




  




  delSpace(data:any)
  {
    this.services.deleteSpace(data).subscribe(response=>{
      this.ngOnInit();
      this.show=false;
      console.log(response)
    }, error => {
      console.log(error);

    });
  }
}

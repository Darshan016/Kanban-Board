import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ServicesService } from '../services/services.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private route:Router,private service: ServicesService) { }
  responseData!:any;
  token!:any;

  ngOnInit(): void
  {

  }

  loginform=new FormGroup({
    email:new FormControl('',Validators.required),
    password:new FormControl('',Validators.required)
  })

  get email() { return this.loginform.controls['email'];}
  get password() { return this.loginform.controls['password'];}


  login()
  {
    console.log(this.loginform.value);
    this.service.loginUser(this.loginform.value).subscribe(async data1=>{
      console.log(data1);
      this.responseData = data1
      console.log(this.responseData.userName);
      this.service.userName = this.responseData.userName;
      this.service.email= this.responseData.email;
      this.token = this.responseData.token;
      if(this.token!==null)
      {
        // this.service.profileC=true;
      }

      sessionStorage.setItem('token',this.token)

      if(sessionStorage.getItem('token')!=null)
      this.service.isLoggedIn = true;
      this.service.email = this.email.value;
      console.log(this.service.email);
      // alert("Login Successful")
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-right',
        iconColor: 'green',
        customClass: {
          popup: 'colored-toast'
        },
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: true
      })
      await Toast.fire({
        icon: 'success',
        title: 'Success'
      })


      this.route.navigate(['/workspace'])
    },
    async error=>{
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-right',
        iconColor: 'red',
        customClass: {
          popup: 'colored-toast'
        },
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: true
      })
      await Toast.fire({
        icon: 'error',
        title: 'invalid credentials'
      })
      console.log(error);
    })

  }



}

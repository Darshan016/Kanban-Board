import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { ServicesService } from '../services/services.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  maxDate: Date = new Date();

  profileForm = this.fb.group({
    email:['',[Validators.required,Validators.pattern("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]+[\\w]$")]],
    userName: ['', Validators.required],
    password: ['', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]],
    confirmPassword: ['', [Validators.required, Validators.pattern(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/)]],
    // phone: ['', [Validators.pattern(/^[789]\d{9,9}$/)]],
  }, { validators: [this.mustMatchValidator] });

  constructor(private fb: FormBuilder,private service:ServicesService) { this.maxDate.setDate(this.maxDate.getDate() - 1); }

  get email() { return this.profileForm.get('email');}

  get userName() { return this.profileForm.get("userName") }

  // get phone() { return this.profileForm.get("phone"); }

  get password() { return this.profileForm.get("password"); }

  get confirmPassword() { return this.profileForm.get("confirmPassword"); }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.profileForm.value);
    console.log(this.profileForm.value);
    this.service.registerUser(this.profileForm.value)
     this.profileForm.reset();
    }

  mustMatchValidator(fg: AbstractControl) {
    const passwordValue = fg.get("password")?.value;
    const confirmPasswordValue = fg.get("confirmPassword")?.value;
    if (!passwordValue || !confirmPasswordValue) {
      return null;
    }
    if (passwordValue != confirmPasswordValue) {
        return { mustMatch: false }
    }
    return null;
  }
}



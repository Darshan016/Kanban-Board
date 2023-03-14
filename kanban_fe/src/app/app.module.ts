import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { FontAwesomeModule, FaIconLibrary  } from '@fortawesome/angular-fontawesome';
import { ProfileComponent } from './profile/profile.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatRadioModule} from '@angular/material/radio';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { WorkspacesComponent } from './workspaces/workspaces.component';
import { WorkspaceDashboardComponent } from './workspace-dashboard/workspace-dashboard.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatDialogModule} from '@angular/material/dialog';
import { UpdateWorkSpaceComponent } from './update-work-space/update-work-space.component'; 
import { MatSelectModule } from "@angular/material/select";
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatExpansionModule} from '@angular/material/expansion'; 
import { CdkVirtualForOf } from '@angular/cdk/scrolling';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { LandingPageComponent } from './landing-page/landing-page.component';
@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    ProfileComponent,
    LoginComponent,
    WorkspacesComponent,
    WorkspaceDashboardComponent,
    UpdateWorkSpaceComponent,
    LandingPageComponent,
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,FontAwesomeModule,
    BrowserAnimationsModule,
    FormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSnackBarModule,
    MatCardModule,
    MatIconModule,
    HttpClientModule,
    MatMenuModule,
    MatDialogModule,
    MatSelectModule,MatSidenavModule,MatListModule,
    DragDropModule,MatExpansionModule,CdkVirtualForOf,ScrollingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

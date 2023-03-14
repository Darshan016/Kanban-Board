import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { WorkspaceDashboardComponent } from './workspace-dashboard/workspace-dashboard.component';
import { WorkspacesComponent } from './workspaces/workspaces.component';

const routes: Routes = [ {
  path: "signup",
  component: ProfileComponent
},
{
  path: "login",
  component: LoginComponent
},
{
  path: "workspace",
  component: WorkspacesComponent
},
{
  path: "dash",
  component: WorkspaceDashboardComponent
},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

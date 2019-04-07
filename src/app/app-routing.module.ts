import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardContainerComponent } from 'src/app/modules/muzix/components/card-container/card-container.component';
import { WishListComponent } from 'src/app/modules/muzix/components/wish-list/wish-list.component';
import { RegisterComponent } from 'src/app/modules/authentication/components/register/register.component';
import { LoginComponent } from 'src/app/modules/authentication/components/login/login.component';
import { AuthGuardService } from 'src/app/modules/muzix/auth-guard.service';

const routes: Routes = [
{
  path: 'Australia' ,
  component: CardContainerComponent,
  data :  { country: 'Australia'}
},
{
  path: '' ,
  component: LoginComponent,
},
{
  path: 'login' ,
  component: LoginComponent,
},
{
  path: 'register' ,
  component: RegisterComponent,
},
{
  path: 'India' ,
  component: CardContainerComponent,
  data :  { country: 'India'}
},

{
  path: 'Spain' ,
  component: CardContainerComponent,
  data :  { country: 'Spain'}
},

{
  path: 'wishList' ,
  component: WishListComponent,
  canActivate: [AuthGuardService]
},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

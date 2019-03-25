import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardContainerComponent } from 'src/app/modules/muzix/components/card-container/card-container.component';
import { WishListComponent } from 'src/app/modules/muzix/components/wish-list/wish-list.component';

const routes: Routes = [
{
  path: '' ,
  component: CardContainerComponent,
  data :  { country: 'Australia'}
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
  component: WishListComponent
},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

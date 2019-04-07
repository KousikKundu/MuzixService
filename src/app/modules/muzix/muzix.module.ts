import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardContainerComponent } from './components/card-container/card-container.component';
import { CardComponent } from './components/card/card.component';
import { HeaderComponent } from './components/header/header.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { WishListComponent } from './components/wish-list/wish-list.component';
import { FooterComponent } from './components/footer/footer.component';
import { DialogComponent } from './components/dialog/dialog.component';
import {AngularmaterialModule} from '../angularmaterial/angularmaterial.module';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorService } from './interceptor.service';



@NgModule({
  declarations: [CardContainerComponent, CardComponent, HeaderComponent, WishListComponent, FooterComponent, DialogComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AngularmaterialModule
  ],
  exports: [
    CardContainerComponent,
    CardComponent,
    HeaderComponent,
    AppRoutingModule,
    FooterComponent
  ],
  providers: [
    MuzixService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }
  ],
  entryComponents: [
    DialogComponent
  ]
})
export class MuzixModule { }

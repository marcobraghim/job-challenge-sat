import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FridgeComponent } from './pages/fridge/fridge.component';
import { HomepageComponent } from './homepage/homepage.component';
import { ManageComponent } from './pages/fridge/manage/manage.component';

@NgModule({
  declarations: [
    AppComponent,
    FridgeComponent,
    HomepageComponent,
    ManageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

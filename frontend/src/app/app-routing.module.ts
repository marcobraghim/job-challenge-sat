import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FridgeComponent } from './pages/fridge/fridge.component';
import { HomepageComponent } from './homepage/homepage.component';
import { ManageComponent } from './pages/fridge/manage/manage.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'fridge/manage', component: ManageComponent },
  { path: 'fridge/manage/:id', component: ManageComponent },
  { path: 'fridge', component: FridgeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

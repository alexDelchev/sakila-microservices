import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CategoryPickerComponent } from './category-picker/category-picker.component';
import { FilmBrowserComponent } from './film-browser/film-browser.component';

const routes: Routes = [
  { path: '', component: CategoryPickerComponent },
  { path: 'category/:categoryId', component: FilmBrowserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

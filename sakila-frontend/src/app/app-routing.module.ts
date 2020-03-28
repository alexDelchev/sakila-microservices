import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CategoryPickerComponent } from './category-picker/category-picker.component';
import { FilmBrowserComponent } from './film-browser/film-browser.component';
import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  { path: '', component: CategoryPickerComponent },
  { path: 'category/:category', component: FilmBrowserComponent },
  { path: 'cart', component: CartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

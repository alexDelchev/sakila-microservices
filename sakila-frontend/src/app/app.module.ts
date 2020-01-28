import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Provider, forwardRef } from '@angular/core';

import { environment } from '../environments/environment';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ApiRequestInterceptor } from './config/request.interceptor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AddressApiModule } from '@api/generated/address/address-api.module';
import { FilmApiModule } from '@api/generated/film/film-api.module';
import { PaymentApiModule } from '@api/generated/payment/payment-api.module';
import { StoreApiModule } from '@api/generated/store/store-api.module';

import { FilmComponent } from './film/film.component';
import { CategoryPickerComponent } from './category-picker/category-picker.component';
import { CategoryComponent } from './category/category.component';
import { FilmBrowserComponent } from './film-browser/film-browser.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatFormFieldModule, MatSelectModule, MatInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';

export const API_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => ApiRequestInterceptor),
  multi: true
};

@NgModule({
  declarations: [
    AppComponent,
    FilmComponent,
    CategoryPickerComponent,
    CategoryComponent,
    FilmBrowserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AddressApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/address/' }),
    FilmApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/film/' }),
    PaymentApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/payment/' }),
    StoreApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/store/' }),
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    FormsModule
  ],
  providers: [
    ApiRequestInterceptor, API_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

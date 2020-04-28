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
import { StoreReadApiModule } from '@api/generated/store/read/store-read-api.module';
import { StoreWriteApiModule } from '@api/generated/store/write/store-write-api.module';

import { FilmComponent } from './film/film.component';
import { CategoryPickerComponent } from './category-picker/category-picker.component';
import { CategoryComponent } from './category/category.component';
import { FilmBrowserComponent } from './film-browser/film-browser.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatFormFieldModule, MatSelectModule, MatInputModule, MatButtonModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { CartComponent } from './cart/cart.component';

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
    FilmBrowserComponent,
    HeaderComponent,
    FooterComponent,
    CartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AddressApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/address/' }),
    FilmApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/film/' }),
    PaymentApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/payment/' }),
    StoreReadApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/store/read/' }),
    StoreWriteApiModule.forRoot({ rootUrl: environment.apiRootUrl + '/api/store/write/' }),
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    FormsModule
  ],
  providers: [
    ApiRequestInterceptor, API_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

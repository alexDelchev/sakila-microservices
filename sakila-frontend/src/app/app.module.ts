import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Provider, forwardRef } from '@angular/core';

import { environment } from '../environments/environment';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ApiRequestInterceptor } from './config/request.interceptor';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

export const API_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => ApiRequestInterceptor),
  multi: true
};

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    ApiRequestInterceptor, API_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

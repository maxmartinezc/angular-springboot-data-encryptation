import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HelloComponent } from './hello/hello.component';
import { HeaderInterceptor } from './interceptors/header.interceptor';
import { AesCryptoService } from './services/aes-crypto.service';
import { RequestService } from './services/request.service';
import { RequestInterceptor } from './interceptors/request.interceptor';
import { ResponseInterceptor } from './interceptors/response.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    HelloComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    RequestService, 
    AesCryptoService,
    { provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptor, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: ResponseInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

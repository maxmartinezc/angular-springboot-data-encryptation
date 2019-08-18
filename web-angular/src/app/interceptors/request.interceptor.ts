import { HttpHandler, HttpInterceptor, HttpRequest, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Injectable, ɵConsole } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { AesCryptoService } from '../services/aes-crypto.service';
import { WrapperRequestResponseDto } from '../dto/wrapper-request-response.dto';
import { map, catchError } from 'rxjs/operators';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    constructor(private aesCryptoService: AesCryptoService){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
        let body: WrapperRequestResponseDto = { data: this.aesCryptoService.encrypt(request.body) };
        request = request.clone({body: body});

    return next.handle(request);

    }
  
}
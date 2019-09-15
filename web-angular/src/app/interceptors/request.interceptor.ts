import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { AesCryptoService } from '../services/aes-crypto.service';


@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    constructor(private aesCryptoService: AesCryptoService){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
        return this.aesCryptoService.encrypt(request.body).pipe(
            map(encrypted => typeof request.body === 'string' ? encrypted : new JsonReplacer(encrypted)),
            map(body => request.clone({ body })),
            switchMap(req => next.handle(req))
        );
    }
}

class JsonReplacer{
    constructor(private content: string){}

    toJSON(): string {
        return this.content;
    }
}
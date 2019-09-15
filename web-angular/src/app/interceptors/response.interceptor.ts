import { HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, tap} from 'rxjs/operators';
import { AesCryptoService } from '../services/aes-crypto.service';
import { Observable } from 'rxjs';

@Injectable()
export class ResponseInterceptor implements HttpInterceptor {

    constructor(private aesCryptoService: AesCryptoService){}

    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
      ): Observable<HttpEvent<any>> {
    
        return next.handle(request)
        .pipe(
            map(event => {
              if (event instanceof HttpResponse) {
                if(event.body != null)
                  return event.clone({ body: JSON.parse(this.aesCryptoService.decrypt(event.body)) });
              }
              return event;
            }, error => {
             // http response status code
                console.log("----response----");
                console.error("status code:");
                console.error(error.status);
                console.error(error.message);
                console.log("--- end of response---");
  
            })
          )
    
      }
    }
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RequestDto } from '../dto/request-dto';
import { environment } from '../../environments/environment';
import { ResponseDto } from '../dto/response-dto';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient) { 
  }

  invoke(payload: RequestDto){
      return this.http.post<ResponseDto>(environment.backendUrl, payload);
  }
}

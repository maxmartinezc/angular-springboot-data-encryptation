import { Component } from '@angular/core';
import { RequestService } from '../services/request.service';
import { AesCryptoService } from '../services/aes-crypto.service';
import { ResponseDto } from '../dto/response-dto';

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent {

  response: ResponseDto;
  constructor(private requestService: RequestService) { }

  send(txtName: string){
    this.requestService.invoke({ name: txtName}).subscribe(data => {
          this.response = data;
    });
  }

}

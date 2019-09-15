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

  list: Array<ResponseDto>;
  constructor(private requestService: RequestService) { }

  send(txtName: string){
    this.requestService.post({ name: txtName}).subscribe(data => {
        this.getList();
    });
  }

  getList(){
    this.requestService.list().subscribe(data => this.list = data)
  }
}

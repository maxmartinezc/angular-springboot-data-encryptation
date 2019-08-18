import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import * as CryptoJS from 'crypto-js';


@Injectable({
  providedIn: 'root'
})
export class AesCryptoService {

  aesKey: string = environment.aesSecretKey;
  constructor() { }

  //The set method is use for encrypt the value.
  encrypt(data: any){

    var rkEncryptionKey = CryptoJS.enc.Base64.parse(this.aesKey);
    var rkEncryptionIv = CryptoJS.enc.Base64.parse('5D9r9ZVzEYYgha93/aUK2w==');
    
    var utf8Stringified = CryptoJS.enc.Utf8.parse(JSON.stringify(data));

    var encrypted = CryptoJS.AES.encrypt(utf8Stringified.toString(), rkEncryptionKey, {
        mode: CryptoJS.mode.CBC, 
        padding: CryptoJS.pad.Pkcs7, 
        iv: rkEncryptionIv
    });
    
    return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
  }

  //The get method is use for decrypt the value.
  decrypt(data:any){

    console.log(data);
    var rkEncryptionKey = CryptoJS.enc.Base64.parse(this.aesKey);
    var rkEncryptionIv = CryptoJS.enc.Base64.parse('5D9r9ZVzEYYgha93/aUK2w==');
    var decrypted = CryptoJS.AES.decrypt(data, rkEncryptionKey, {
        iv: rkEncryptionIv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });

    return decrypted.toString(CryptoJS.enc.Utf8);

    // var rkEncryptionKey = CryptoJS.enc.Base64.parse(this.aesKey);
    // var rkEncryptionIv = CryptoJS.enc.Base64.parse('5D9r9ZVzEYYgha93/aUK2w==');
    
    // var decrypted = CryptoJS.AES.encrypt(data, rkEncryptionKey, {
    //     mode: CryptoJS.mode.CBC, 
    //     padding: CryptoJS.pad.Pkcs7, 
    //     iv: rkEncryptionIv
    // });
    
    // return decrypted.toString(CryptoJS.enc.Utf8);

  }
}

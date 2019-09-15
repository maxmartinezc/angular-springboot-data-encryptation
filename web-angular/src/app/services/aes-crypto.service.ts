import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import * as CryptoJS from 'crypto-js';
import { of } from 'rxjs/internal/observable/of';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AesCryptoService {

    aesKey: string = environment.aesSecretKey;
    aesEncryptionIv: string = environment.aesEncryptionIv;
    constructor() { }

    //The set method is use for encrypt the value.
    encrypt(data: any): Observable<string>{

        var encryptionKey = CryptoJS.enc.Base64.parse(this.aesKey);
        var encryptionIv = CryptoJS.enc.Base64.parse(this.aesEncryptionIv);
        
        var utf8Stringified = CryptoJS.enc.Utf8.parse((typeof data === 'string' ? data : JSON.stringify(data)));

        var encrypted = CryptoJS.AES.encrypt(utf8Stringified.toString(), encryptionKey, {
            mode: CryptoJS.mode.CBC, 
            padding: CryptoJS.pad.Pkcs7, 
            iv: encryptionIv
        });
        
        return of(encrypted.ciphertext.toString(CryptoJS.enc.Base64));
    }

    //The get method is use for decrypt the value.
    decrypt(data:any){
        var rkEncryptionKey = CryptoJS.enc.Base64.parse(this.aesKey);
        var rkEncryptionIv = CryptoJS.enc.Base64.parse(this.aesEncryptionIv);
        var decrypted = CryptoJS.AES.decrypt(data, rkEncryptionKey, {
            iv: rkEncryptionIv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    }
}

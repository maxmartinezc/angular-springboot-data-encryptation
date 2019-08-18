package cl.dataencryptation.apigateway.components;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 


public class AESUtil {
 
    public static String encrypt(String strToEncrypt, String secret){
        try {
        	SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(secret), "AES");
        	AlgorithmParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode("5D9r9ZVzEYYgha93/aUK2w=="));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
	
	public static String decrypt(String cipherText, String secret){

		try {
			 SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(secret), "AES");
			 AlgorithmParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode("5D9r9ZVzEYYgha93/aUK2w=="));
			 byte[] decodeBase64 = Base64.getDecoder().decode(cipherText);
			 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			 cipher.init(Cipher.DECRYPT_MODE, key, iv);
			 return new String(cipher.doFinal(decodeBase64), "UTF-8");
		} catch (Exception e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
	    }
    }
}
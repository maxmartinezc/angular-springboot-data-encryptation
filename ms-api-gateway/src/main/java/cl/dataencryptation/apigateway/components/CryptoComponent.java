package cl.dataencryptation.apigateway.components;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.dataencryptation.apigateway.config.AESKeysProperties;
import cl.dataencryptation.apigateway.config.AESKeysProperties.TrustedChannel;

/**
 * Component para encryptar y desencryptar con AES
 * @author manuteko
 *
 */
@Component
public class CryptoComponent {
 
	@Autowired
	private AESKeysProperties aesKeysProperties;
	
    public String encrypt(String strToEncrypt, String channelId){
        try {
        	TrustedChannel channel = getChannelById(channelId);
        	
        	SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(channel.getKey()), "AES");
        	AlgorithmParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(channel.getIv()));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
	
	public String decrypt(String cipherText, String channelId){
		try {
			TrustedChannel channel = getChannelById(channelId);
			 SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(channel.getKey()), "AES");
			 AlgorithmParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(channel.getIv()));
			 byte[] decodeBase64 = Base64.getDecoder().decode(cipherText);
			 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			 cipher.init(Cipher.DECRYPT_MODE, key, iv);
			 return new String(cipher.doFinal(decodeBase64), "UTF-8");
		} catch (Exception e) {
			 throw new RuntimeException(e);
	    }
    }
	
	/**
	 * Retorna el objeto TrustedChannel asociado al id del canal
	 * @param id
	 * @return
	 */
	private TrustedChannel getChannelById(String id) {
		TrustedChannel channel = aesKeysProperties.getTrustedChannel()
							.stream()
							.filter(n -> n.getId().equals(id))
							.findFirst()
							.orElseGet(null);
		
		return channel;
	}
}
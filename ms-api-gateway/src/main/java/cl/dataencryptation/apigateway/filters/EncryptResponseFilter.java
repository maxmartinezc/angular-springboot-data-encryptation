package cl.dataencryptation.apigateway.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cl.dataencryptation.apigateway.components.AESUtil;
import cl.dataencryptation.apigateway.config.AESKeysProperties;
import cl.dataencryptation.apigateway.config.AESKeysProperties.TrustedChannel;
import cl.dataencryptation.apigateway.dto.WrapperRequestResponseDto;

public class EncryptResponseFilter extends ZuulFilter {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private AESKeysProperties aesKeysProperties;
	
	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 999;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		
		try (final InputStream responseDataStream = context.getResponseDataStream()) {
			   final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
			   
			   String channelId = context.getRequest().getHeader("channel-id");
			   String secret = this.getSecretKeyByChannel(channelId);
			   
			   WrapperRequestResponseDto response = new WrapperRequestResponseDto(
					   AESUtil.encrypt(responseData, secret)
			   );

			   context.setResponseBody(this.mapper.writeValueAsString(response));

			} catch (IOException e) {
		}
		
		return null;
	}
	
	/**
	 * Retorna el secret para encryptar la respuesta según el cliente que lo solicita
	 * @param id
	 * @return
	 */
	private String getSecretKeyByChannel(String channel) {
		TrustedChannel secret = aesKeysProperties.getTrustedChannel()
							.stream()
							.filter(n -> n.getId().equals(channel))
							.findFirst()
							.orElseGet(null);
		String key = null;
		if (secret != null)
			key = secret.getKey();
		
		return key;
	}
}

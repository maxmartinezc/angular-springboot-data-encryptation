package cl.dataencryptation.apigateway.filters;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;

import cl.dataencryptation.apigateway.components.AESUtil;
import cl.dataencryptation.apigateway.config.AESKeysProperties;
import cl.dataencryptation.apigateway.config.AESKeysProperties.TrustedChannel;
import cl.dataencryptation.apigateway.dto.WrapperRequestResponseDto;

public class DecryptRequestFilter extends ZuulFilter {

	@Autowired
	private AESKeysProperties aesKeysProperties;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 999;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		try {
			
			RequestContext context = RequestContext.getCurrentContext();
			
			if (context.getRequest().getMethod().equals(HttpMethod.OPTIONS.toString())) {
				return null;
			}
			
			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			
			WrapperRequestResponseDto requestJsonObj = this.mapper.readValue(body, WrapperRequestResponseDto.class); 
			
			String channelId = context.getRequest().getHeader("channel-id");
			String secret = this.getSecretKeyByChannel(channelId);

			byte[] bytes = Hex.decodeHex(AESUtil.decrypt(requestJsonObj.getData(), secret).toCharArray());
			
			context.setRequest(new HttpServletRequestWrapper(context.getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(bytes);
                }

                @Override
                public int getContentLength() {
                    return bytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return bytes.length;
                }
	        });
		}
		catch (Exception e) {
			rethrowRuntimeException(e);
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

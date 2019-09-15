package cl.dataencryptation.apigateway.filters;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

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

import cl.dataencryptation.apigateway.components.CryptoComponent;
/**
 * Filtro para desencryptar el request entrante
 * @author max.martinez.c@gmail.com
 *
 */
public class DecryptRequestFilter extends ZuulFilter {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private CryptoComponent crypto;
	
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
			
			if (!body.equals("")) {
				
				String data = this.mapper.readValue(body, String.class);
				String channelId = context.getRequest().getHeader("channel-id");
	
				byte[] bytes = Hex.decodeHex(this.crypto.decrypt(data, channelId).toCharArray());
				
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
		}
		catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}
}

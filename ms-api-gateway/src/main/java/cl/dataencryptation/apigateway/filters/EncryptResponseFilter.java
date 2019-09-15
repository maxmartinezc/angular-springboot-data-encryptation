package cl.dataencryptation.apigateway.filters;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cl.dataencryptation.apigateway.components.CryptoComponent;

/**
 * Filtro para encryptar la respuesta de un request
 * @author max.martinez.c@gmail.com
 *
 */
public class EncryptResponseFilter extends ZuulFilter {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private CryptoComponent crypto;
	
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
			   if (!responseData.equals("")) {

				   context.setResponseBody(
						   this.mapper.writeValueAsString(
								   this.crypto.encrypt(responseData, 
												   context.getRequest().getHeader("channel-id"))
								   )
						   );
			   }

		} 
		catch (IOException e) {	
			rethrowRuntimeException(e);
		}
		
		return null;
	}
}
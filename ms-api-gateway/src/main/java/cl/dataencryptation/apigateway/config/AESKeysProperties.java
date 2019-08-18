package cl.dataencryptation.apigateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app.security.aes")
public class AESKeysProperties {

	private List<TrustedChannel> trustedChannel = new ArrayList<>();

	public List<TrustedChannel> getTrustedChannel() {
		return trustedChannel;
	}
	
	public void setTrustedChannel(List<TrustedChannel> trustedChannel) {
		this.trustedChannel = trustedChannel;
	}

	public static class TrustedChannel {
		private String id;
		private String key;
		
		public TrustedChannel() {
			super();
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TrustedChannel [id=");
			builder.append(id);
			builder.append(", key=");
			builder.append(key);
			builder.append("]");
			return builder.toString();
		}
	}
}

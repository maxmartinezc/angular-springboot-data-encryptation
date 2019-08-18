package cl.dataencryptation.apigateway.dto;

public class WrapperRequestResponseDto {
	private String data;

	public WrapperRequestResponseDto() {
		super();
	}
	
	public WrapperRequestResponseDto(String data) {
		super();
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WrapperRequestResponseDto [data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}

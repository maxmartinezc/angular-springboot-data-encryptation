package cl.dataencryptation.hello.dto;

public class RequestDto {

	private String name;

	public RequestDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestDto [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
}

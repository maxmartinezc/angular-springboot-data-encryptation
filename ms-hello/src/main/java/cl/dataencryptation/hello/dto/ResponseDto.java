package cl.dataencryptation.hello.dto;

public class ResponseDto {

	private String name;
	private String msg;
	
	public ResponseDto(String name, String msg) {
		super();
		this.name = name;
		this.msg = msg;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseDto [name=");
		builder.append(name);
		builder.append(", msg=");
		builder.append(msg);
		builder.append("]");
		return builder.toString();
	}
	
	
}

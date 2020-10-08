package query.utils;

public class SQLData {
	
	private String attribute;
	private Object value;
	private SQL_TYPE type;
	
	public SQLData(String attribute, Object value, SQL_TYPE type) {
		this.attribute = attribute;
		this.value = value;
		this.type = type;
	}
	
	public String getAttribute() {
		return attribute;
	}
	public Object getValue() {
		return value;
	}
	public SQL_TYPE getType() {
		return type;
	}
	
	
}

package communication;

public class Attribute {
	
	private String name_;
	private String string_value_;
	private int int_value_;
	private Boolean bool_value_;
	private String value_type_;
	
	public Attribute(String name, Boolean bool_value) {
		super();
		this.name_ = name;
		this.bool_value_ = bool_value;
		this.value_type_ = "bool";
	}

	public Attribute(String name, String string_value) {
		super();
		this.name_ = name;
		this.string_value_ = string_value;
		this.value_type_ = "string";
	}

	public Attribute(String name_, int int_value) {
		super();
		this.name_ = name_;
		this.int_value_ = int_value;
		this.value_type_ = "int";
	}

	public boolean Name(String name) {
		return name_.equals(name);
	}
	
	public String Name() {
		return name_;
	}

	public boolean Equals(String value) {
		return string_value_.equals(value);
	}
	
	public boolean Equals(Boolean value) {
		return bool_value_ == value;
	}
	
	public boolean Equals(int value) {
		return int_value_ == value;
	}
	
	public boolean GreaterThan(int value) {
		return int_value_ > value;
	}
	
	public boolean LessThan(int value) {
		return int_value_ < value;
	}
	
	public String Value() {
		if (value_type_.equals("int"))
			return String.valueOf(int_value_);
		else if (value_type_.equals("bool"))	
			return String.valueOf(bool_value_);
		else if (value_type_.equals("string"))
			return String.valueOf(string_value_);		
		else return "Unknown";
	}
	
	public static Attribute Parse(String name, String value) {
		if (value.equals("yes"))
			return new Attribute(name, true);
		if (value.equals("no"))
			return new Attribute(name, false);
		if (value.contains("number_"))
			return new Attribute(name, Integer.parseInt(value.substring(7)));
		else
			return new Attribute(name, value);
	}
}

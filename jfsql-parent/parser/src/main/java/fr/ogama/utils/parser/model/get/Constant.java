package fr.ogama.utils.parser.model.get;

public class Constant implements Expression {
	public static final int UNKNOWN = -1;
	public static final int COLUMNNAME = 0;
	public static final int NULL = 1;
	public static final int NUMBER = 2;
	public static final int STRING = 3;
	int type_ = Constant.UNKNOWN;
	String value = null;

	/**
	 * Create a new constant, given its name and type.
	 */
	public Constant(String v, int typ) {
		value = new String(v);
		type_ = typ;
	}

	/*
	 * @return the constant value
	 */
	public String getValue() {
		return value;
	}

	/*
	 * @return the constant type
	 */
	public int getType() {
		return type_;
	}

	public String toString() {
		if (type_ == STRING)
			return '\'' + value + '\'';
		else
			return value;
	}
}

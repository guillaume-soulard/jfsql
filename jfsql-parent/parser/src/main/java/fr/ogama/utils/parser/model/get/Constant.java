package fr.ogama.utils.parser.model.get;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.properties.Content;
import fr.ogama.utils.parser.model.get.properties.CreationDate;
import fr.ogama.utils.parser.model.get.properties.File;
import fr.ogama.utils.parser.model.get.properties.LastAccessDate;
import fr.ogama.utils.parser.model.get.properties.LastUpdateDate;
import fr.ogama.utils.parser.model.get.properties.Name;
import fr.ogama.utils.parser.model.get.properties.Owner;
import fr.ogama.utils.parser.model.get.properties.Parent;
import fr.ogama.utils.parser.model.get.properties.Path;
import fr.ogama.utils.parser.model.get.properties.Size;
import fr.ogama.utils.parser.model.get.properties.Status;
import fr.ogama.utils.parser.model.get.properties.Type;

public class Constant implements Expression {
	public static final int UNKNOWN = -1;
	public static final int FILE_PROPERTY = 0;
	public static final int NULL = 1;
	public static final int NUMBER = 2;
	public static final int STRING = 3;
	int type_ = Constant.UNKNOWN;
	String value = null;

	/**
	 * Create a new constant, given its name and type.
	 */
	public Constant(String v, int type) {
		value = new String(v);
		type_ = type;
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

	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		
		Comparable formatedValue = null;
		
		if (type_ == NUMBER) {
			formatedValue = Double.valueOf(value);
		} else {
			formatedValue = value;
		}
		
		return Arrays.asList(formatedValue);
	}

	public static Constant getProperty(String propertyName) {

		propertyName = propertyName != null ? propertyName.toLowerCase() : null;

		if (FileProperties.FILE.getLabel().equals(propertyName)) {
			return new File();
		}

		if (FileProperties.CONTENT.getLabel().equals(propertyName)) {
			return new Content();
		}

		if (FileProperties.CREATION_DATE.getLabel().equals(propertyName)) {
			return new CreationDate();
		}

		if (FileProperties.LAST_ACCESS_DATE.getLabel().equals(propertyName)) {
			return new LastAccessDate();
		}

		if (FileProperties.LAST_UPDATE_DATE.getLabel().equals(propertyName)) {
			return new LastUpdateDate();
		}

		if (FileProperties.NAME.getLabel().equals(propertyName)) {
			return new Name();
		}

		if (FileProperties.OWNER.getLabel().equals(propertyName)) {
			return new Owner();
		}

		if (FileProperties.PARENT.getLabel().equals(propertyName)) {
			return new Parent();
		}

		if (FileProperties.PATH.getLabel().equals(propertyName)) {
			return new Path();
		}

		if (FileProperties.STATUS.getLabel().equals(propertyName)) {
			return new Status();
		}

		if (FileProperties.TYPE.getLabel().equals(propertyName)) {
			return new Type();
		}

		if (FileProperties.SIZE.getLabel().equals(propertyName)) {
			return new Size();
		}

		throw new IllegalArgumentException(propertyName + " is undefined");
	}
}

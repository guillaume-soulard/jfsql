package fr.ogama.utils.parser.model.get.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.FileProperties;
import fr.ogama.utils.parser.model.get.FilePropertiesUtils;

public class CreationDate extends Constant {

	public CreationDate() {
		super(FileProperties.CREATION_DATE.getLabel(), Constant.FILE_PROPERTY);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		try {
			return Arrays.asList((Comparable) FilePropertiesUtils
					.getCreationDate(((java.io.File) params.get("file"))));
		} catch (Exception e) {
			throw new JFSQLExecutionException(e.getMessage(), e);
		}
	}
}
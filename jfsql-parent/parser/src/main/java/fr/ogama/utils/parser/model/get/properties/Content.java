package fr.ogama.utils.parser.model.get.properties;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.FileProperties;
import fr.ogama.utils.parser.model.get.FilePropertiesUtils;

public class Content extends Constant {

	public Content() {
		super(FileProperties.CONTENT.getLabel(), Constant.FILE_PROPERTY);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		try {
			return Arrays.asList((Comparable) FilePropertiesUtils
					.getContent(((java.io.File) params.get("file"))));
		} catch (IOException e) {
			throw new JFSQLExecutionException(e.getMessage(), e);
		}
	}
}
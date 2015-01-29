package fr.ogama.utils.parser.model.get.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.FileProperties;

public class Path extends Constant {

	public Path() {
		super(FileProperties.PATH.getLabel(), Constant.FILE_PROPERTY);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		return Arrays.asList((Comparable) ((java.io.File) params.get("file"))
				.getPath());
	}
}
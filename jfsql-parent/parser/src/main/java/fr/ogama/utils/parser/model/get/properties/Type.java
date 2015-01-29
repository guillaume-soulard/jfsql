package fr.ogama.utils.parser.model.get.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.FileProperties;
import fr.ogama.utils.parser.model.get.FileType;

public class Type extends Constant {

	public Type() {
		super(FileProperties.TYPE.getLabel(), Constant.FILE_PROPERTY);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		java.io.File file = (java.io.File) params.get("file");

		return Arrays.asList((Comparable) (file.isFile() ? FileType.FILE.getLabel()
				: FileType.DIRECTORY.getLabel()));
	}
}
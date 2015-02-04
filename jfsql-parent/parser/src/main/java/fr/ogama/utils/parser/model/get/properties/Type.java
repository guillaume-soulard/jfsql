package fr.ogama.utils.parser.model.get.properties;

import java.util.ArrayList;
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

		List<Comparable> types = new ArrayList<Comparable>();
		
		if (file.isFile()) {
			types.add(FileType.FILE.getLabel());
		}
		
		if (file.isDirectory()) {
			types.add(FileType.DIRECTORY.getLabel());
		}
		
		if (file.isHidden()) {
			types.add(FileType.HIDDEN.getLabel());
		}
		
		return types;
	}
}
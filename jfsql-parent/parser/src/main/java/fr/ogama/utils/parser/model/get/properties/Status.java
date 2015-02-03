package fr.ogama.utils.parser.model.get.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.FileProperties;
import fr.ogama.utils.parser.model.get.FileStatus;

public class Status extends Constant {

	public Status() {
		super(FileProperties.STATUS.getLabel(), Constant.FILE_PROPERTY);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		java.io.File file = (java.io.File) params.get("file");
		FileStatus status = FileStatus.UNACCISSIBLE;

		if (file.canRead() && !file.canWrite() && !file.canExecute()) {
			status = FileStatus.READABLE;
		}

		if (file.canRead() && file.canWrite() && !file.canExecute()) {
			status = FileStatus.WRITABLE;
		}

		// windows case
		if (file.canRead() && !file.canWrite() && file.canExecute()) {
			status = FileStatus.EXECUTABLE;
		}
		
		if (file.canRead() && file.canWrite() && file.canExecute()) {
			status = FileStatus.EXECUTABLE;
		}

		return Arrays
				.asList((Comparable) status.getLabel());
	}
}
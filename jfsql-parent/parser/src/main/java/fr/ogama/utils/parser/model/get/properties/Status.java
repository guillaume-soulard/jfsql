package fr.ogama.utils.parser.model.get.properties;

import java.util.ArrayList;
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
		
		List<Comparable> status = new ArrayList<Comparable>();
				
		if (file.canRead()) {
			status.add(FileStatus.READABLE.getLabel());
		}

		if (file.canWrite()) {
			status.add(FileStatus.WRITABLE.getLabel());
		}

		if (file.canExecute()) {
			status.add(FileStatus.EXECUTABLE.getLabel());
		}
		
		if (!file.canRead() && !file.canWrite() && !file.canExecute()) {
			status.add(FileStatus.UNACCISSIBLE.getLabel());
		}

		return status;
	}
}
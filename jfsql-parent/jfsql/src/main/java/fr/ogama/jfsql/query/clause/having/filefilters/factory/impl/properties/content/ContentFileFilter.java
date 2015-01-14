package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.content;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class ContentFileFilter extends AbstractFileFilter {

	private List<String> contents;

	public ContentFileFilter(List<String> contents) {
		this.contents = contents;
	}

	@Override
	protected boolean acceptFile(File file, String name) {
		if (file.isDirectory()) {
			return false;
		}

		return super.acceptFile(file, name);
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.LIKE);
		addSupportedOperator(Operators.IN);
		addSupportedOperator(Operators.MATCH);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		try {
			if (file.canRead()) {
				return FilePropertyHelper.getContent(file);
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		return JFSQLUtils.toComparable(contents);
	}
}

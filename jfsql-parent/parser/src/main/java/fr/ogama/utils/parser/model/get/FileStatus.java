package fr.ogama.utils.parser.model.get;

public enum FileStatus {
	UNACCISSIBLE("unaccessible"),
	READABLE("readable"),
	WRITABLE("writable"),
	EXECUTABLE("executable");
	
	private String label;

	private FileStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}

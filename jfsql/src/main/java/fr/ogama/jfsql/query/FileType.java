package fr.ogama.jfsql.query;

public enum FileType {
	
	FILE("file"),
	DIRECTORY("directory");
	
	private String label;
	
	private FileType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}

package fr.ogama.utils.parser.model.get;

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

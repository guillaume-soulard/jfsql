package fr.ogama.utils.parser.model.get;

public enum FileStatus {
	UNACCISSIBLE("unaccessible",0),
	READABLE("readable", 1),
	WRITABLE("writable", 2),
	EXECUTABLE("executable", 3);
	
	private String label;
	
	private int index;

	private FileStatus(String label, int index) {
		this.label = label;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getLabel() {
		return label;
	}
}

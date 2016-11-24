package model;

public class Result {
	private String path;
	private String speaker;
	private String title;
	private String act;
	private String scene;
	private String stageDir;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTitle(String title) {
		this.title = title;
		System.out.println("Title set to " + title);
	}

	public void setAct(String act) {
		this.act = act;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	@Override
	public String toString() {
		String s = "<html>"
				+ title + "<br>"
				+ act + "<br>"
				+ scene + "<br>"
				+ "-"
				+ "</html>";

		return s;
	}
}
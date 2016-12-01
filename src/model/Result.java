package model;

public class Result {
	private String path;
	private String speaker;
	private String title;
	private String act;
	private String scene;
	private int hits;
	private float score;
	private String resultType;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Override
	public String toString() {
		switch (resultType) {
			case "Scene Titles":
			case "Stage Directions":
				return "<html>"
						+ title + "<br>"
						+ act + "<br>"
						+ scene + "<br>"
						+ "-"
						+ "</html>";
			case "Dialogue":
				return "<html>"
						+ title + "<br>"
						+ act + "<br>"
						+ scene + "<br>"
						+ speaker + "<br>"
						+ "-"
						+ "</html>";
			case "Simple":
			case "All":
			default:
				return "<html>"
						+ title + "<br>"
						+ "-"
						+ "</html>";
		}
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
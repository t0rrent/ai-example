package example.ai.application.model;

import java.util.List;

public class DrawingCommitParam {

	private List<Boolean> compressedDrawing;
	
	private char character;
	
	private long contributorId;

	public List<Boolean> getCompressedDrawing() {
		return compressedDrawing;
	}

	public void setCompressedDrawing(final List<Boolean> compressedDrawing) {
		this.compressedDrawing = compressedDrawing;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(final char character) {
		this.character = character;
	}

	public long getContributorId() {
		return contributorId;
	}

	public void setContributorId(final long contributorId) {
		this.contributorId = contributorId;
	}
	
}

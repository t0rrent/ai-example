package example.ai.application.model;

import java.util.List;

public class Contribution {

	private List<Boolean> compressedDrawing;
	
	private char character;
	
	private long dateAdded;

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

	public long getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(final long dateAdded) {
		this.dateAdded = dateAdded;
	}

}

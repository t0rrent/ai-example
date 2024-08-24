package example.ai.core.model;

import java.util.List;

/**
 * A user-contributed data entry use for training and testing.
 * 
 * The compressedDrawing is a binarized representation of a draw which uses RLE for the 0 and 1 values.
 * The binary list is split into groups of 6, the first flag represents the value of the run and the rest of the values are a 5 bit integer which determine the length of the run.
 */
public class DrawingDataEntry {

	private List<Boolean> compressedDrawing;
	
	private char character;

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
	
}

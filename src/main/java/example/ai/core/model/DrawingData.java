package example.ai.core.model;

import java.util.List;

public class DrawingData {

	private List<Boolean> drawing;
	
	private char character;

	public List<Boolean> getDrawing() {
		return drawing;
	}

	public void setDrawing(final List<Boolean> drawing) {
		this.drawing = drawing;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(final char character) {
		this.character = character;
	}
	
}

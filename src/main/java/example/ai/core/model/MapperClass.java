package example.ai.core.model;

public class MapperClass<T> {
	
	private final Class<T> type;
	
	public MapperClass(final Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

}

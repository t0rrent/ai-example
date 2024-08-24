package example.ai.core.model;

public class SimpleAliasClass<T> {
	
	private final Class<T> type;

	public SimpleAliasClass(final Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

}

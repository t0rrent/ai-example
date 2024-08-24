package example.ai.core.hk2.factory;

import org.glassfish.hk2.api.Factory;

import example.ai.core.model.MapperClass;

public class MapperClassWrapperFactory<T> implements Factory<MapperClass<T>> {
	
	private final MapperClass<T> mapperClass;
	
	public MapperClassWrapperFactory(final Class<T> mapperClass) {
		this.mapperClass = new MapperClass<>(mapperClass);
	}

	@Override
	public MapperClass<T> provide() {
		return mapperClass;
	}

	@Override
	public void dispose(final MapperClass<T> instance) {
	}

}

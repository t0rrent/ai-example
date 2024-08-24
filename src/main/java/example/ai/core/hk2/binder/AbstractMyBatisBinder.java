package example.ai.core.hk2.binder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;

import example.ai.core.hk2.factory.MapperClassWrapperFactory;
import example.ai.core.hk2.factory.SimpleAliasClassWrapperFactory;
import example.ai.core.model.MapperClass;
import example.ai.core.model.SimpleAliasClass;
import jakarta.inject.Singleton;

public abstract class AbstractMyBatisBinder extends AbstractBinder {

	protected <T> ServiceBindingBuilder<MapperClass<T>> bindMapper(final Class<T> mapperType) {
		return bindFactory(new MapperClassWrapperFactory<T>(mapperType)).to(MapperClass.class);
	}
	
	protected void addSimpleAlias(final Class<?> paramType) {
		bindFactory(new SimpleAliasClassWrapperFactory<>(paramType)).to(SimpleAliasClass.class).in(Singleton.class);
	}

}

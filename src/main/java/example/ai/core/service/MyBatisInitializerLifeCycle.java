package example.ai.core.service;

import java.util.Collection;

import org.apache.ibatis.session.Configuration;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import example.ai.core.hk2.binder.SimpleFactoryBinder;
import example.ai.core.hk2.factory.MyBatisMapperFactory;
import example.ai.core.model.MapperClass;
import example.ai.core.model.SimpleAliasClass;
import example.ai.mybatis.BooleanCollectionTypeHandler;
import jakarta.inject.Inject;

public class MyBatisInitializerLifeCycle implements LifeCycle {
	
	private final ServiceLocator serviceLocator;
	
	private final SessionManagerService sessionManagerService;

	private final Configuration config;
	
	@Inject
	public MyBatisInitializerLifeCycle(
			final ServiceLocator serviceLocator,
			final SessionManagerService sessionManagerService,
			final Configuration config
	) {
		this.serviceLocator = serviceLocator;
		this.sessionManagerService = sessionManagerService;
		this.config = config;
	}

	@Override
	public void start() {
		bindTypeHandlers();
		bindAliases();
		bindMappers();
	}

	private void bindTypeHandlers() {
		config.getTypeHandlerRegistry().register(BooleanCollectionTypeHandler.class);
	}

	@Override
	public void stop() {
	}

	private void bindMappers() {
		@SuppressWarnings("rawtypes")
		final Collection<Class> mapperClasses = serviceLocator.getAllServices(MapperClass.class)
				.stream()
				.map(MapperClass::getType)
				.map((mapperClass) -> (Class) mapperClass)
				.toList();
		mapperClasses.forEach(config::addMapper);
		mapperClasses.forEach(this::bindMapper);
	}

	private <T> void bindMapper(final Class<T> mapperType) {
		ServiceLocatorUtilities.bind(
				serviceLocator,
				new SimpleFactoryBinder<T, T>(new MyBatisMapperFactory<>(mapperType, sessionManagerService), mapperType)
		);
 	}
	
	private void bindAliases() {
		serviceLocator.getAllServices(SimpleAliasClass.class)
				.stream()
				.map(SimpleAliasClass::getType)
				.forEach(config.getTypeAliasRegistry()::registerAlias);
	}
	
}

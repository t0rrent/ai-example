package example.ai.core.hk2.binder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import example.ai.core.hk2.factory.DataSourceConfigFactory;
import example.ai.core.model.DataSourceConfig;
import example.ai.core.service.DatabaseInitializerLifeCycle;
import example.ai.core.service.JDBCService;
import example.ai.core.service.LifeCycle;
import example.ai.core.service.PostgresJDBCService;
import jakarta.inject.Singleton;

public class DatabaseOnlyBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bindFactory(DataSourceConfigFactory.class).to(DataSourceConfig.class).in(Singleton.class);
		bind(DatabaseInitializerLifeCycle.class).to(LifeCycle.class).in(Singleton.class).ranked(100);
		bind(PostgresJDBCService.class).to(JDBCService.class).in(Singleton.class);
	}

}

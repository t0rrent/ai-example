package example.ai.core.hk2.binder;

import org.apache.ibatis.session.Configuration;

import example.ai.application.service.ModelMapperService;
import example.ai.application.service.ModelService;
import example.ai.core.hk2.factory.DataSourceConfigFactory;
import example.ai.core.hk2.factory.KafkaConfigFactory;
import example.ai.core.hk2.factory.MyBatisConfigurationFactory;
import example.ai.core.model.DataSourceConfig;
import example.ai.core.model.KafkaConfig;
import example.ai.core.model.ModelCommit;
import example.ai.core.service.JDBCService;
import example.ai.core.service.LifeCycle;
import example.ai.core.service.MyBatisInitializerLifeCycle;
import example.ai.core.service.MyBatisSessionManagerService;
import example.ai.core.service.PostgresJDBCService;
import example.ai.core.service.SessionManagerService;
import example.ai.mapper.ModelMapper;
import example.ai.pipeline.service.DataEncodingService;
import example.ai.pipeline.service.SimpleDataEncodingService;
import jakarta.inject.Singleton;

public class AIExampleCoreBinder extends AbstractMyBatisBinder {

	@Override
	protected void configure() {
		addAliases();
		addMappers();
		addServices();
		addConfig();
	}

	private void addAliases() {
		addSimpleAlias(ModelCommit.class);
	}

	private void addMappers() {
		bindMapper(ModelMapper.class);
	}

	private void addServices() {
		bind(PostgresJDBCService.class).to(JDBCService.class).in(Singleton.class);
		bind(MyBatisInitializerLifeCycle.class).to(LifeCycle.class).in(Singleton.class).ranked(99);
		bind(MyBatisSessionManagerService.class).to(SessionManagerService.class).in(Singleton.class);
		bind(ModelMapperService.class).to(ModelService.class).in(Singleton.class);
		bind(SimpleDataEncodingService.class).to(DataEncodingService.class).in(Singleton.class);
	}

	private void addConfig() {
		bindFactory(DataSourceConfigFactory.class).to(DataSourceConfig.class).in(Singleton.class);
		bindFactory(MyBatisConfigurationFactory.class).to(Configuration.class).in(Singleton.class);
		bindFactory(KafkaConfigFactory.class).to(KafkaConfig.class).in(Singleton.class);
	}

}

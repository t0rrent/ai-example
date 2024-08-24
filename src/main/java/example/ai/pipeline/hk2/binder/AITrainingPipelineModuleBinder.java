package example.ai.pipeline.hk2.binder;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;

import example.ai.core.hk2.binder.AbstractMyBatisBinder;
import example.ai.core.model.DrawingDataEntry;
import example.ai.core.service.LifeCycle;
import example.ai.mapper.DataRetrievalMapper;
import example.ai.pipeline.hk2.factory.MultiLayerConfigurationFactory;
import example.ai.pipeline.service.DataRetrievalMapperService;
import example.ai.pipeline.service.DataRetrievalService;
import example.ai.pipeline.service.TrainingEventProducerService;
import example.ai.pipeline.service.TrainingEventService;
import example.ai.pipeline.service.TrainingLifeCycle;
import jakarta.inject.Singleton;

public class AITrainingPipelineModuleBinder extends AbstractMyBatisBinder {

	@Override
	protected void configure() {
		addAliases();
		addMappers();
		addServices();
		addConfig();
	}

	private void addAliases() {
		addSimpleAlias(DrawingDataEntry.class);
	}

	private void addMappers() {
		bindMapper(DataRetrievalMapper.class);
	}

	private void addServices() {
		bind(DataRetrievalMapperService.class).to(DataRetrievalService.class).in(Singleton.class);
		bind(TrainingLifeCycle.class).to(LifeCycle.class).in(Singleton.class);
		bind(TrainingEventProducerService.class)
				.to(TrainingEventService.class)
				.to(LifeCycle.class)
				.in(Singleton.class);
	}

	private void addConfig() {
		bindFactory(MultiLayerConfigurationFactory.class).to(MultiLayerConfiguration.class).in(Singleton.class);
	}

}

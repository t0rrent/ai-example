package example.ai.application.hk2.binder;

import example.ai.application.model.Contribution;
import example.ai.application.model.ContributionsQueryParam;
import example.ai.application.model.Contributor;
import example.ai.application.model.ContributorsQueryParam;
import example.ai.application.model.DrawingCommitParam;
import example.ai.application.model.SetBanParam;
import example.ai.application.service.AuthMapperService;
import example.ai.application.service.AuthService;
import example.ai.application.service.ContributionMapperService;
import example.ai.application.service.ContributionService;
import example.ai.application.service.ModelTestingService;
import example.ai.application.service.TestingService;
import example.ai.application.service.TrainingEventConsumerLifeCycle;
import example.ai.core.hk2.binder.AbstractMyBatisBinder;
import example.ai.core.service.LifeCycle;
import example.ai.mapper.AuthMapper;
import example.ai.mapper.ContributionMapper;
import jakarta.inject.Singleton;

public class AIExampleApplicationModuleBinder extends AbstractMyBatisBinder {

	@Override
	protected void configure() {
		addAliases();
		addMappers();
		addServices();
	}

	private void addAliases() {
		addSimpleAlias(DrawingCommitParam.class);
		addSimpleAlias(ContributorsQueryParam.class);
		addSimpleAlias(Contributor.class);
		addSimpleAlias(Contribution.class);
		addSimpleAlias(ContributionsQueryParam.class);
		addSimpleAlias(SetBanParam.class);
	}

	private void addMappers() {
		bindMapper(ContributionMapper.class);
		bindMapper(AuthMapper.class);
	}

	private void addServices() {
		bind(ContributionMapperService.class).to(ContributionService.class).in(Singleton.class);
		bind(ModelTestingService.class)
				.to(TestingService.class)
				.to(LifeCycle.class)
				.in(Singleton.class);
		bind(TrainingEventConsumerLifeCycle.class).to(LifeCycle.class).in(Singleton.class);
		bind(AuthMapperService.class).to(AuthService.class).in(Singleton.class);
	}

}

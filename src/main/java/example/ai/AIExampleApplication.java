package example.ai;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.glassfish.hk2.utilities.Binder;

import example.ai.application.api.AdminAPI;
import example.ai.application.api.ContributionAPI;
import example.ai.application.api.TestingAPI;
import example.ai.application.hk2.binder.AIExampleApplicationModuleBinder;
import example.ai.core.ApplicationFramework;
import example.ai.core.hk2.binder.AIExampleCoreBinder;
import example.ai.core.hk2.binder.DatabaseOnlyBinder;
import example.ai.core.model.Pipeline;
import example.ai.pipeline.hk2.binder.AITrainingPipelineModuleBinder;
import jakarta.ws.rs.core.Application;

public class AIExampleApplication extends Application {

	private static final Collection<Supplier<Binder>> APPLICATION_BINDERS = Arrays.asList(
			AIExampleApplicationModuleBinder::new,
			AIExampleCoreBinder::new
	);

	private static final Collection<Supplier<Binder>> DATABASE_ONLY_BINDERS = Arrays.asList(
			DatabaseOnlyBinder::new
	);

	private static final Pipeline AI_INTERNAL_PIPELINE = Pipeline.of(
			"aiTrainer",
			AITrainingPipelineModuleBinder::new,
			AIExampleCoreBinder::new
	);

	private static final Collection<Supplier<Application>> APPLICATION_CLASSES = Arrays.asList(
			AIExampleApplication::new
	);
	
	public static void main(final String[] args) {
		final ApplicationFramework server = new ApplicationFramework(APPLICATION_BINDERS, DATABASE_ONLY_BINDERS, APPLICATION_CLASSES, AI_INTERNAL_PIPELINE);
		server.start();
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> apis = new HashSet<>();
		apis.add(ContributionAPI.class);
		apis.add(TestingAPI.class);
		apis.add(AdminAPI.class);
		return apis;
	}
	
}

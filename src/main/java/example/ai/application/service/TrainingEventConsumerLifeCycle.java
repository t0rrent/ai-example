package example.ai.application.service;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.ai.core.model.KafkaConfig;
import example.ai.core.service.LifeCycle;
import example.ai.pipeline.service.TrainingEventProducerService;
import jakarta.inject.Inject;

public class TrainingEventConsumerLifeCycle implements LifeCycle {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TrainingEventConsumerLifeCycle.class);
	
	private final TestingService testingService;
	
	private final ScheduledExecutorService scheduledExecutorService;
	
	private final KafkaConfig kafkaConfig;

	private KafkaConsumer<String, String> consumer;

	private ScheduledFuture<?> pollSchedule;
	
	@Inject
	public TrainingEventConsumerLifeCycle(
			final TestingService testingService,
			final ScheduledExecutorService scheduledExecutorService,
			final KafkaConfig kafkaConfig
	) {
		this.testingService = testingService;
		this.scheduledExecutorService = scheduledExecutorService;
		this.kafkaConfig = kafkaConfig;
	}

	@Override
	public void start() {
		if (kafkaConfig.isEnabled()) {
	        this.consumer = new KafkaConsumer<>(kafkaConfig.getConsumerProperties());
	        this.consumer.subscribe(Collections.singletonList(TrainingEventProducerService.TRAINING_EVENT_TOPIC));
	        schedulePoll();
		}
	}

	@Override
	public synchronized void stop() {
		if (kafkaConfig.isEnabled()) {
			pollSchedule.cancel(true);
			this.consumer.close();
		}
	}

	private synchronized void schedulePoll() {
		pollSchedule = scheduledExecutorService.schedule(this::poll, 0, TimeUnit.MILLISECONDS);
	}
	
	private void poll() {
		final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(20000));
		if (!records.isEmpty()) {
			LOGGER.info("New model created");
			testingService.onNewModelAvailable(StreamSupport.stream(records.spliterator(), false)
					.filter((record) -> record.key().equals(TrainingEventProducerService.NEW_MODEL_EVENT_KEY))
					.map((record) -> Double.valueOf(record.value()))
					.mapToDouble(Double::valueOf)
					.max()
					.getAsDouble());
		}
		schedulePoll();
	}

}

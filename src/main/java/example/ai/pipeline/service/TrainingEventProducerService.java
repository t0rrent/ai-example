package example.ai.pipeline.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import example.ai.core.model.KafkaConfig;
import example.ai.core.service.LifeCycle;
import jakarta.inject.Inject;

public class TrainingEventProducerService implements TrainingEventService, LifeCycle {
	
	public static final String TRAINING_EVENT_TOPIC = "training_events";
	
	public static final String NEW_MODEL_EVENT_KEY = "new_model";
	
	private final KafkaConfig kafkaConfig;
	
	private KafkaProducer<String, String> producer;

	@Inject
	public TrainingEventProducerService(final KafkaConfig kafkaConfig) {
		this.kafkaConfig = kafkaConfig;
	}

	@Override
	public void pushNewModelEvent(final double score) {
		if (kafkaConfig.isEnabled()) {
	        final ProducerRecord<String, String> record = new ProducerRecord<>(TRAINING_EVENT_TOPIC, NEW_MODEL_EVENT_KEY, String.valueOf(score));
	        producer.send(record);
		}
	}

	@Override
	public void start() {
		if (kafkaConfig.isEnabled()) {
			producer = new KafkaProducer<>(kafkaConfig.getProducerProperties());
		}
	}

	@Override
	public void stop() {
		if (kafkaConfig.isEnabled()) {
			producer.close();
		}
	}

}

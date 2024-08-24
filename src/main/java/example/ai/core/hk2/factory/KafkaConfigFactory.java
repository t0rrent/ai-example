package example.ai.core.hk2.factory;

import java.util.Properties;

import org.glassfish.hk2.api.Factory;

import example.ai.core.model.KafkaConfig;
import example.ai.core.util.SimpleConfigUtil;

public class KafkaConfigFactory implements Factory<KafkaConfig> {

	private static final String GROUP_ID = "example.ai";

	@Override
	public KafkaConfig provide() {
		KafkaConfig config = new KafkaConfig();
		config.setConsumerProperties(getConsumerProperties());
		config.setProducerProperties(getProducerProperties());
		config.setEnabled(SimpleConfigUtil.getConfig("kafkaEnabled", true));
		return config;
	}
	
    private Properties getConsumerProperties() {
        final Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", SimpleConfigUtil.getConfig("kafkaServer", "localhost:9092"));
        consumerProperties.put("group.id", GROUP_ID);
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("auto.offset.reset", "earliest");
        return consumerProperties;
	}
	
    private Properties getProducerProperties() {
    	final Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers", SimpleConfigUtil.getConfig("kafkaServer", "localhost:9092"));
        producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return producerProperties;
	}

	@Override
	public void dispose(final KafkaConfig instance) {
	}

}

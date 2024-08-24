package example.ai.core.model;

import java.util.Properties;

public class KafkaConfig {
	
	private Properties producerProperties;
	
	private Properties consumerProperties;
	
	private boolean enabled;

	public Properties getProducerProperties() {
		return producerProperties;
	}

	public void setProducerProperties(final Properties producerProperties) {
		this.producerProperties = producerProperties;
	}

	public Properties getConsumerProperties() {
		return consumerProperties;
	}

	public void setConsumerProperties(final Properties consumerProperties) {
		this.consumerProperties = consumerProperties;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

}

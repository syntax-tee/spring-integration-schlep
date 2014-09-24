package org.springframework.integration.schlep.dsl;

import org.springframework.integration.dsl.core.MessagingProducerSpec;
import org.springframework.integration.schlep.inbound.SchlepInboundChannelAdapter;

import com.netflix.schlep.consumer.MessageConsumer;


public class SchlepInboundChannelAdapterSpec extends MessagingProducerSpec<SchlepInboundChannelAdapterSpec, SchlepInboundChannelAdapter> {
	
	public SchlepInboundChannelAdapterSpec(MessageConsumer consumer, Class<?> messageType) {
		super(new SchlepInboundChannelAdapter(consumer, messageType));
	}
	
	public SchlepInboundChannelAdapterSpec autoAcknowledge(boolean autoAcknowledge)
	{
		this.target.setAutoAcknowledge(autoAcknowledge);
		return this;
	}	

}

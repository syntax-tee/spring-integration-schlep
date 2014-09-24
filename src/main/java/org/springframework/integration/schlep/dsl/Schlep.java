package org.springframework.integration.schlep.dsl;

import com.netflix.schlep.consumer.MessageConsumer;
import com.netflix.schlep.producer.MessageProducer;

public  abstract class Schlep {
	
	public static SchlepInboundChannelAdapterSpec inboundAdapter(MessageConsumer consumer, Class<?> messageType) {
		return new SchlepInboundChannelAdapterSpec(consumer, messageType);
	}
	
	public static SchlepOutboundEndpointSpec outboundAdapter(MessageProducer producer)
	{
		return new SchlepOutboundEndpointSpec(producer);
	}
	
}

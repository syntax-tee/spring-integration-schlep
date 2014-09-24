package org.springframework.integration.schlep.dsl;

import org.springframework.integration.dsl.core.MessageHandlerSpec;
import org.springframework.integration.schlep.outbound.SchlepOutboundEndpoint;

import com.netflix.schlep.producer.MessageProducer;

public class SchlepOutboundEndpointSpec extends MessageHandlerSpec<SchlepOutboundEndpointSpec, SchlepOutboundEndpoint>  {

	private final SchlepOutboundEndpoint endpoint;
	
	public SchlepOutboundEndpointSpec(MessageProducer producer) {
		this.endpoint = new SchlepOutboundEndpoint(producer);
	}
	
	public SchlepOutboundEndpointSpec extractPayload(boolean extractPayload)
	{
		this.endpoint.setExtractPayload(extractPayload);
		return this;
	}

	@Override
	protected SchlepOutboundEndpoint doGet() {
		return this.endpoint;
	}	

}

package org.springframework.integration.schlep.outbound;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

import com.netflix.schlep.producer.MessageProducer;
import com.netflix.schlep.producer.OutgoingMessage;
import com.netflix.schlep.producer.OutgoingMessage.Builder;

public class SchlepOutboundEndpoint extends AbstractMessageHandler {

	private final MessageProducer messageProducer;
	private boolean extractPayload;
	
	public SchlepOutboundEndpoint(final MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
		this.extractPayload = true;
	}
	
	public SchlepOutboundEndpoint(final MessageProducer messageProducer, final boolean extractPayload) {
		this.messageProducer = messageProducer;
		this.extractPayload = extractPayload;
	}
	
	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Object payload = extractPayload ? message.getPayload() : message;
		Builder messageBuilder = OutgoingMessage.builder().withEntity(payload);
		if(message.getHeaders() != null && !message.getHeaders().isEmpty())
		{
			messageBuilder.withAttributes(message.getHeaders());
		}
		messageProducer.send(messageBuilder.build()).toBlocking();
	}

	public void setExtractPayload(boolean extractPayload) {
		this.extractPayload = extractPayload;
	}
	
}

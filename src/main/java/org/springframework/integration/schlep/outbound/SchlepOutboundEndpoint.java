package org.springframework.integration.schlep.outbound;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.integration.schlep.headers.DefaultSchlepHeaderMapper;
import org.springframework.messaging.Message;

import com.netflix.schlep.Notification;
import com.netflix.schlep.producer.MessageProducer;
import com.netflix.schlep.producer.OutgoingMessage;
import com.netflix.schlep.producer.OutgoingMessage.Builder;

public class SchlepOutboundEndpoint extends AbstractReplyProducingMessageHandler {

	private final MessageProducer messageProducer;
	private boolean extractPayload;
	private HeaderMapper<Map<String,Object>> headerMapper;
	
	public SchlepOutboundEndpoint(final MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
		this.extractPayload = true;
		this.headerMapper = new DefaultSchlepHeaderMapper();
	}
	
	public SchlepOutboundEndpoint(final MessageProducer messageProducer, final boolean extractPayload) {
		this.messageProducer = messageProducer;
		this.extractPayload = extractPayload;
	}
	
	public void setExtractPayload(boolean extractPayload) {
		this.extractPayload = extractPayload;
	}

	@Override
	protected Object handleRequestMessage(Message<?> message) {
		Object payload = extractPayload ? message.getPayload() : message;
		Builder messageBuilder = OutgoingMessage.builder().withEntity(payload);
		Map<String,Object> schlepHeaders = new HashMap<String, Object>();
		headerMapper.fromHeaders(message.getHeaders(), schlepHeaders);
		messageBuilder.withAttributes(schlepHeaders);
		Notification<OutgoingMessage> result = messageProducer.send(messageBuilder.build()).toBlocking().single();
		if(result.hasError())
		{
			throw new RuntimeException("Failed sending Schlep message", result.getError());
		}
		else
		{
			return result;
		}
	}
	
}

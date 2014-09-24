package org.springframework.integration.schlep.inbound;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;

import com.netflix.schlep.Ackable;
import com.netflix.schlep.consumer.IncomingMessage;
import com.netflix.schlep.consumer.MessageConsumer;
import com.netflix.schlep.consumer.MessageHandler;

public class SchlepInboundChannelAdapter<T> extends MessageProducerSupport {

	private final MessageConsumer messageConsumer;
	private boolean autoAcknowledge;
	private Class<T> messageType;
	private Logger logger = LoggerFactory.getLogger(SchlepInboundChannelAdapter.class);
	
	public SchlepInboundChannelAdapter(final MessageConsumer messageConsumer, Class<T> messageType) {
		this.messageConsumer = messageConsumer;
		this.autoAcknowledge = true;
		this.messageType=messageType;
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		
		messageConsumer.observe().subscribe(new MessageHandler() {
            @Override
            public void call(Ackable<IncomingMessage> ackable) {
                try {	
                	AbstractIntegrationMessageBuilder<T> message = getMessageBuilderFactory()
					.withPayload(ackable.getValue().getEntity(messageType))
					.setHeaderIfAbsent("ackable", ackable);
                	for(Entry<String, Object> attribute : ackable.getValue().getAttributes().entrySet())
                	{
                		message.setHeaderIfAbsent(attribute.getKey(), attribute.getValue());
                	}
					sendMessage(message.build());
                   if(autoAcknowledge)
                   {
                    ackable.ack();
                   }
                } catch (Exception e) {
                    ackable.error(e);
                    logger.error("Failed to process Schlep message", e);
                }
            }
        });
		
		if(isAutoStartup())
		{
			try {
				messageConsumer.resume();
			} catch (Exception e) {
				throw new RuntimeException("Failed to start Schlep Consumer", e);
			}
		}
	}
	
	@Override
	public String getComponentType() {
		return "schlep:inbound-channel-adapter";
	}	
	

	@Override
	protected void doStart() {
		try {
			messageConsumer.resume();
		} catch (Exception e) {
			throw new RuntimeException("Failed to start Schlep Consumer", e);
		}
	}

	@Override
	protected void doStop() {
		try {
			messageConsumer.pause();
		} catch (Exception e) {
			throw new RuntimeException("Failed to start Schlep Consumer", e);
		}
	}

	public boolean isAutoAcknowledge() {
		return autoAcknowledge;
	}

	public void setAutoAcknowledge(boolean autoAcknowledge) {
		this.autoAcknowledge = autoAcknowledge;
	}	
}

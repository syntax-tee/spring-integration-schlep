package org.springframework.integration.schlep.headers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.ObjectUtils;

public class DefaultSchlepHeaderMapper implements HeaderMapper<Map<String, Object>> {
	
	private static final String[] TRANSIENT_HEADER_NAMES = new String[] {
		MessageHeaders.ID,
		MessageHeaders.ERROR_CHANNEL,
		MessageHeaders.REPLY_CHANNEL,
		MessageHeaders.TIMESTAMP,
		SchlepMessageHeaders.ACKABLE,
		SchlepMessageHeaders.OBSERVABLE
		
	};

	@Override
	public Map<String, Object> toHeaders(Map<String, Object> source) {
		Map<String, Object> headers = new HashMap<String, Object>();
		for(Entry<String,Object> entry : headers.entrySet())
		{
			if(shouldMapHeader(entry.getKey()))
			{
				headers.put(entry.getKey(), entry.getValue());
			}
		}
		return headers;
	}
	
	
	private boolean shouldMapHeader(String headerName)
	{
		return !ObjectUtils.containsElement(TRANSIENT_HEADER_NAMES, headerName);
	}


	@Override
	public void fromHeaders(MessageHeaders headers, Map<String, Object> target) {
		for(Entry<String,Object> entry : headers.entrySet())
		{
			if(shouldMapHeader(entry.getKey()))
			{
				target.put(entry.getKey(), entry.getValue());
			}
		}
		
	}



	

}

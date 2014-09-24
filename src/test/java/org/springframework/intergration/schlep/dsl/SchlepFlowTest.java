package org.springframework.intergration.schlep.dsl;

public class SchlepFlowTest {
	
//	@Test
//	public void testSchlepOutboundFlow() throws Exception {
//		this.amqpOutboundInput.send(MessageBuilder.withPayload("hello through the amqp")
//				.setHeader("routingKey", "foo")
//				.build());
//		Message<?> receive = null;
//		int i = 0;
//		do {
//			receive = this.amqpReplyChannel.receive();
//			if (receive != null) {
//				break;
//			}
//			Thread.sleep(100);
//			i++;
//		} while (i < 10);
//
//		assertNotNull(receive);
//		assertEquals("HELLO THROUGH THE AMQP", receive.getPayload());
//	}

}

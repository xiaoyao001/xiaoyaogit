package com.boot.org.simple;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class GETMSGToExchangeTwo {


	private static final String EXCHANG_ENAME = "test_exchange";

	private static final String QUEUE_NAME = "simple_queue_two";

	private static final boolean durable = false;

	private static final boolean exclusive = false;

	private static final boolean autoDelete = false;

	private static final Map<String, Object> arguments = null;

	public static void main(String[] args) throws IOException, TimeoutException {
		// 创建一个mq链接
		Connection connection = MQConnectUtil.getMqConnectUtil();
		// 从链接中获取一个通道
		final Channel channel = connection.createChannel();
		try {
			// 队列声明
			channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, arguments);
			channel.queueBind(QUEUE_NAME, EXCHANG_ENAME, "two_key");
			// 消息应答
			boolean autoAck = false;
			channel.basicQos(5);
			// 监听队列//定义一个 consumer消费者
			channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					// TODO Auto-generated method stub
					String result = new String(body, "UTF-8");
					System.out.println("[recv2号]" + result);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

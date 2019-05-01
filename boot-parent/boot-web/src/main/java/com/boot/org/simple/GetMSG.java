package com.boot.org.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 消息消费者
 * 
 * @author xiaoyao
 *
 */
public class GetMSG {

	private static final String QUEUE_NAME = "simple_queue";

	public static void main(String[] args)
			throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connection = null;
		Channel channel = null;
		try {
			// 创建一个mq链接
			connection = MQConnectUtil.getMqConnectUtil();
			// 从链接中获取一个通道
			channel = connection.createChannel();
			// 队列声明
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			//定义一个  consumer消费者
			DefaultConsumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					// TODO Auto-generated method stub
					String result = new String(body, "UTF-8");
					System.out.println("[recv]" + result);
				}
			};
			// 监听队列
			channel.basicConsume(QUEUE_NAME, true, consumer);
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

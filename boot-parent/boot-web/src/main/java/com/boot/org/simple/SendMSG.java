package com.boot.org.simple;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消息发送者
 * @author xiaoyao
 *
 */
public class SendMSG {
	
	private static final String QUEUE_NAME = "simple_queue";
	
	private static final boolean durable = true;
	
	private static final boolean exclusive = false;
	
	private static final boolean autoDelete = false;
	
	private static final Map<String, Object> arguments = null;

	public static void main(String[] args) throws InterruptedException {
		Connection connection = null;
		Channel channel = null;
		try {
			//创建一个mq链接
			connection = MQConnectUtil.getMqConnectUtil();
			//从链接中获取一个通道
			channel = connection.createChannel();
			//创建队列-----设置消息的持久化
			channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, arguments);	
			//提供者一次只发5个
			channel.basicQos(5);
			for(int i =0;i<50;i++) {
				String msg = "我发送的第"+(i+1)+"的一个请求";
				channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
				System.out.println("消息发送成功");
				Thread.sleep(i*20);
			}
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				System.out.println("关闭mq队列异常");
			}
			
		}
	}
}

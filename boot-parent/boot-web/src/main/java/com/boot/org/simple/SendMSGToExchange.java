package com.boot.org.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SendMSGToExchange {
	
	private static final String EXCHANG_ENAME = "test_exchange";
	/**fanout不处理路由键*/
	private static final String EXCHANG_ETYPE = "direct";

	public static void main(String[] args) throws InterruptedException {
		Connection connection = null;
		Channel channel = null;
		try {
			//创建一个mq链接
			connection = MQConnectUtil.getMqConnectUtil();
			//从链接中获取一个通道
			channel = connection.createChannel();
			//创建交换机
			channel.exchangeDeclare(EXCHANG_ENAME, EXCHANG_ETYPE);
			channel.basicQos(5);
			for(int i =0;i<50;i++) {
				String msg = "我发送的第"+(i+1)+"的一个请求";
				if(i%2==0) {
					channel.basicPublish(EXCHANG_ENAME, "one_key", null, msg.getBytes());
				}else {
					channel.basicPublish(EXCHANG_ENAME, "two_key", null, msg.getBytes());
				}
				
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
				System.out.println("关闭mq异常");
			}
			
		}
	}
}

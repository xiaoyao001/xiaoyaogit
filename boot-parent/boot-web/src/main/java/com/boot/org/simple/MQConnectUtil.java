package com.boot.org.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * mq连接工具类
 * @author Administrator
 *
 */
public class MQConnectUtil {

	
	/**
	 * 获取mq链接
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Connection getMqConnectUtil() throws IOException, TimeoutException{
		//定义一个连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置服务地址
		factory.setHost("127.0.0.1");
		//AMQP 5672
		factory.setPort(5672);
		//vhost
		factory.setVirtualHost("/v_host");
		//用户名
		factory.setUsername("xiaoyao");
		//密码
		factory.setPassword("xiaoyao");
		//获取链接
		return factory.newConnection();
	}
}

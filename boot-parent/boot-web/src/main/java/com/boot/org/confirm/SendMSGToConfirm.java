package com.boot.org.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.boot.org.simple.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 普通模式
 * 
 * @author xiaoyao
 * @version
 */
public class SendMSGToConfirm {

	private static final String EXCHANG_ENAME = "test_exchange_confirm";
	/** fanout不处理路由键 */
	private static final String EXCHANG_ETYPE = "topic";

	public static void main(String[] args) throws InterruptedException, IOException {
		Connection connection = null;
		Channel channel = null;
		try {
			// 创建一个mq链接
			connection = MQConnectUtil.getMqConnectUtil();
			// 从链接中获取一个通道
			channel = connection.createChannel();
			// 交换机
			channel.exchangeDeclare(EXCHANG_ENAME, EXCHANG_ETYPE);
			channel.basicQos(5);
			//开启事务模式
			channel.confirmSelect();
			for (int i = 0; i < 10; i++) {
				if(i%2==0) {
					String msg = "第" + (i + 1) + "的商品放入";
					channel.basicPublish(EXCHANG_ENAME, "goods.add", null, msg.getBytes());
				}else {
					String msg = "第" + (i + 1) + "的商品拿走";
					channel.basicPublish(EXCHANG_ENAME, "goods.take", null, msg.getBytes());
				}
				Thread.sleep(i * 20);
			}
			if(!channel.waitForConfirms()) {
				System.err.println("消息发送失败");
			}else {
				System.out.println("消息发送成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//回滚事务
			System.out.println("生产者：因失败执行回滚！");
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

package com.boot.org.confirm.listener;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.boot.org.simple.MQConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * 普通模式
 * 
 * @author xiaoyao
 * @version
 */
public class SendMSGToConfirm {

	private static final String EXCHANG_ENAME = "test_exchange_confirm_listener";
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
			
			final SortedSet<Long>confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
			
			channel.addConfirmListener(new ConfirmListener() {
				
				@Override
				public void handleAck(long deliveryTag, boolean multiple) throws IOException {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					if(multiple) {
						System.out.println("---handAck--multiple--");
						confirmSet.headSet(deliveryTag+1).clear();
					}else {
						System.out.println("---handAck--multiple--false");
						confirmSet.remove(deliveryTag);
					}
				}
				
				@Override
				public void handleNack(long deliveryTag, boolean multiple) throws IOException {
					if(multiple) {
						System.out.println("---handNack--multiple--");
						confirmSet.headSet(deliveryTag+1).clear();
					}else {
						System.out.println("---handNack--multiple--false");
						confirmSet.remove(deliveryTag);
					}
				}
			});
			for (int i = 0; i < 10; i++) {
				if(i%2==0) {
					long seqNo = channel.getNextPublishSeqNo();
					String msg = "第" + (i + 1) + "的商品放入";
					channel.basicPublish(EXCHANG_ENAME, "goods.add", null, msg.getBytes());
					confirmSet.add(seqNo);
				}else {
					long seqNo = channel.getNextPublishSeqNo();
					String msg = "第" + (i + 1) + "的商品拿走";
					channel.basicPublish(EXCHANG_ENAME, "goods.take", null, msg.getBytes());
					confirmSet.add(seqNo);
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

package com.boot.org.application.test;

import java.util.UUID;

import com.boot.org.common.HttpConnect;

public class TestThread {

	public static void main(String[] args) throws Exception {
		for(int i =0;i<10;i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result;
					try {
						UUID id = UUID.randomUUID();
						result = HttpConnect.connectServerGet("http://127.0.0.1:1010/bus/queue?orderNo="+id);
						System.err.println("请求号码为"+id+"的消费者请求得到的结果：：："+result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			thread.start();
			Thread.sleep(1000);
		}
	}
}

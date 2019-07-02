package com.boot.org.application.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.boot.org.common.HttpConnect;

public class TestThread {

	public static void main(String[] args) throws Exception {
		final List<String>resultList = new ArrayList<String>();
		for(int i =0;i<20;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String result;
					try {
						UUID id = UUID.randomUUID();
						result = HttpConnect.connectServerGet("http://127.0.0.1:1010/bus/queue?orderNo="+id);
						System.err.println("请求号码为"+id+"的消费者请求得到的结果：：："+result);
						resultList.add(result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("连接异常："+e);
					}
				}
			}).start();
			Thread.sleep((int)(50*Math.random()+50));
		}
		while(true) {
			System.out.println("总投保用户数量：：：："+resultList.size());
			Thread.sleep(2000);
		}
	}
}

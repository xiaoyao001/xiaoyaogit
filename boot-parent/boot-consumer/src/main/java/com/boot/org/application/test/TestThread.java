package com.boot.org.application.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.springframework.web.client.RestTemplate;

public class TestThread {
	
	private static String URL = "http://127.0.0.1:1010/bus/queue";
	
	private static Integer NUMBER = 20;
	
	private static CountDownLatch cdl = new CountDownLatch(NUMBER);
	
	private static RestTemplate rest = new RestTemplate();

	public static void main(String[] args) throws Exception {
		
		for(int i =0;i<NUMBER;i++) {
			new Thread(new TickThread()).start();
			//cdl.countDown();
		}
	}
	
	
	
	public static class TickThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			try {
//				cdl.await();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String orderNo = UUID.randomUUID().toString();
			String str = rest.getForEntity(URL+"?orderNo="+orderNo, String.class).getBody();
			System.out.println(str);
		}
		
	}
}

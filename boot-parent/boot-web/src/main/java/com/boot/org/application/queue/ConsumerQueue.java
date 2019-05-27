package com.boot.org.application.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class ConsumerQueue implements Runnable{

	/**单个线程的返回值*/
	public static Map<String, Object> resultList = new HashMap<String, Object>();
	
	private String orderNo;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//while (true) {
			try {
					System.out.println("consumer开始执行"+Thread.currentThread().getName());
					BlockingQueue<JSONObject>queue = ProduceQueue.produceQueueCacheMap.get(orderNo);
					JSONObject jsonResult = null;
					if (queue.size() != 0) {
						jsonResult = queue.take();
						//JSONObject jsonResult = ProduceQueue.productQueue.take();
						System.out.println("正在执行。。。。。。。。。。。。。。");
						policyAction(jsonResult.get("orderNo").toString());
						jsonResult.put("product", "已完成");
						JSONArray array = null;
						synchronized (resultList) {
							if(resultList.get(jsonResult.get("orderNo")) == null || resultList.get(jsonResult.get("orderNo")).equals("")) {
								jsonResult.put("currentCount", 1);
								array = new JSONArray();
							}else {
								jsonResult.put("currentCount", Integer.parseInt(jsonResult.get("currentCount").toString())+1);
								array = JSONArray.fromObject(JSONObject.fromObject(resultList.get(jsonResult.get("orderNo")).toString()).get("resultList"));
							}
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("currentCount", jsonResult.get("currentCount"));
							array.add(jsonResult);
							jsonObject.put("resultList", array);
							resultList.put(jsonResult.get("orderNo").toString(), jsonObject);
						}
					}
					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}
	
	
	private void policyAction(String orderNo) throws InterruptedException {
		Thread.sleep(3000);
		System.out.println("保险操作为4.5秒");
	}


	public void jobStart(Integer size,String orderNo) {
		ExecutorService executorService = Executors.newFixedThreadPool(size);
		this.orderNo = orderNo;
		ConsumerQueue consumerQueue = new ConsumerQueue();
		consumerQueue.orderNo = orderNo;
		for(int i =0;i<size;i++) {
			executorService.execute(consumerQueue);
		}
		System.out.println(orderNo+"创建了线程池");
		executorService.shutdown();
	}
	
}

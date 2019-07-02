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
	
	private BlockingQueue<JSONObject>queue;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			threadInsurePolicy();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("线程中断异常！！！！！");
		}
	}
	
	
	/**
	 * 争抢过程
	 * @throws InterruptedException
	 */
	private void threadInsurePolicy() throws InterruptedException {
		System.out.println("consumer开始执行"+Thread.currentThread().getName());
		JSONObject jsonResult = null;
		if (queue != null && queue.size() != 0 && !queue.isEmpty()) {
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
	}
	
	
	public ConsumerQueue() {}
	
	public ConsumerQueue(String orderNo, BlockingQueue<JSONObject> queue) {
		this.orderNo = orderNo;
		this.queue = queue;
	}



	private void policyAction(String orderNo) throws InterruptedException {
		Thread.sleep((int)(1500*Math.random()+1500));
		System.out.println("保险操作为3.0秒左右浮动");
	}

	/**
	 * 争抢资源开始
	 * @param size
	 * @param orderNo
	 */
	public void jobStart(String orderNo,Integer size) {
		ExecutorService executorService = Executors.newFixedThreadPool(size);
		ConsumerQueue consumerQueue = new ConsumerQueue(orderNo,ProduceQueue.produceQueueCacheMap.get(orderNo));
		for(int i =0;i<size;i++) {
			executorService.execute(consumerQueue);
		}
		System.out.println(orderNo+"创建了线程池");
		executorService.shutdown();
	}



	public String getOrderNo() {
		return orderNo;
	}



	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	public BlockingQueue<JSONObject> getQueue() {
		return queue;
	}



	public void setQueue(BlockingQueue<JSONObject> queue) {
		this.queue = queue;
	}
	
	
	public static void consumerDestroy(String orderNo) {
		ConsumerQueue.resultList.remove(orderNo);
	}
	
}

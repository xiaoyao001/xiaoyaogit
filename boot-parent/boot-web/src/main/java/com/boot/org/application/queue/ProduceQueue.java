package com.boot.org.application.queue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

/**
 * 定义队列承载平台
 * @author xiaoyao
 * @version 2019.5.27
 */
public class ProduceQueue {
	
	
	//public static BlockingQueue<JSONObject>productQueue = new LinkedBlockingQueue<JSONObject>(10000);

	/**单个请求供应队列声明*/
	private BlockingQueue<JSONObject>queue;
	/**单个请求子线程归属的订单号*/
	private String orderNo;
	/**线程队列承载平台*/
	public static Map<String,BlockingQueue<JSONObject>> produceQueueCacheMap = new LinkedHashMap<String,BlockingQueue<JSONObject>>();
	
	
	public ProduceQueue(String orderNo,Integer size) {
		this.orderNo = orderNo;
		this.queue = new LinkedBlockingQueue<JSONObject>(size);
	}

	
	public void queueStart(List<JSONObject>jsonList,String orderNo) {
		for(JSONObject objs:jsonList) {
			try {
				queue.put(objs);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("队列生成异常" +e);
			}
		}
		produceQueueCacheMap.put(orderNo, queue);
	}
}

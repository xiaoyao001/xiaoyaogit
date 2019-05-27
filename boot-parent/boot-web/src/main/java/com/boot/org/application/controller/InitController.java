package com.boot.org.application.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.org.application.queue.ConsumerQueue;
import com.boot.org.application.queue.ProduceQueue;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/bus")
public class InitController {

		
	@ResponseBody
	@RequestMapping(value="/queue")
	public String sendPdfToMail(HttpServletRequest request,HttpServletResponse response,String orderNo) throws Exception{
		List<JSONObject>mapList = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		int totalPolicyCount = 30;
		json.put("product", "未完成");
		json.put("total", totalPolicyCount);
		json.put("orderNo", orderNo);
		for(int i =0;i<totalPolicyCount;i++) {
			mapList.add(json);
		}
		ProduceQueue produceQueue = new ProduceQueue(orderNo, totalPolicyCount);
		produceQueue.queueStart(mapList,orderNo);
		ConsumerQueue queue = new ConsumerQueue();
		queue.jobStart(mapList.size(),orderNo);
		return cleanAndReturn(orderNo, totalPolicyCount).toString();
	}
	
	
	
	public JSONObject cleanAndReturn(String orderNo,Integer totalPolicyCount) {
		boolean flag = false;
		JSONObject resultJsonObject = new JSONObject();
		while (!flag) {
			if(ConsumerQueue.resultList.get(orderNo) != null && 
					Integer.parseInt(JSONObject.fromObject(ConsumerQueue.resultList.
							get(orderNo).toString()).get("currentCount").toString()) == totalPolicyCount) {
				System.out.println("所有的任务 执行完毕");
				resultJsonObject = JSONObject.fromObject(ConsumerQueue.resultList.get(orderNo));
				ConsumerQueue.resultList.remove(orderNo);
				ProduceQueue.produceQueueCacheMap.remove(orderNo);
				flag = true;
			}
		}
		return resultJsonObject;
	}
}

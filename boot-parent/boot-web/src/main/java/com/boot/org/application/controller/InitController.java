package com.boot.org.application.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.org.application.config.DataSourceConfig;
import com.boot.org.application.queue.ConsumerQueue;
import com.boot.org.application.queue.ProduceQueue;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/bus")
public class InitController {

	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	private ThreadLocal<String>local = new ThreadLocal<String>();

	@ResponseBody
	@RequestMapping(value = "/refresh")
	public void refresh(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(dataSourceConfig.getUrl());
	}
	
	
	@ResponseBody
	@RequestMapping(value="/queue")
	public String sendPdfToMail(HttpServletRequest request,HttpServletResponse response,String orderNo) throws Exception{
		List<JSONObject>mapList = new ArrayList<JSONObject>();
		local.set(orderNo);
		System.out.println(local.get());
		JSONObject json = new JSONObject();
		json.put("product", "未完成");
		json.put("total", 5);
		json.put("orderNo", orderNo);
		mapList.add(json);
		mapList.add(json);
		mapList.add(json);
		mapList.add(json);
		mapList.add(json);
		for(JSONObject maps:mapList) {
			ProduceQueue.queue.put(maps);
		}
		ConsumerQueue queue = new ConsumerQueue();
		queue.jobInit(mapList.size(),orderNo);
		boolean flag = false;
		JSONObject resultJsonObject = new JSONObject();
		while (!flag) {
			/*
			 * System.out.println(JSONObject.fromObject(ConsumerQueue.resultList.
			 * get(orderNo).toString()).get("currentCount").toString());
			 */
			if(ConsumerQueue.resultList.get(orderNo) != null && 
					Integer.parseInt(JSONObject.fromObject(ConsumerQueue.resultList.
							get(orderNo).toString()).get("currentCount").toString()) == 5) {
				System.out.println("所有的任务 执行完毕");
				resultJsonObject = JSONObject.fromObject(ConsumerQueue.resultList.get(orderNo));
				ConsumerQueue.resultList.remove(orderNo);
				flag = true;
			}
		}
		return resultJsonObject.toString();
	}
}

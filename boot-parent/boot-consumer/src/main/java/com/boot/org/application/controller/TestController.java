package com.boot.org.application.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.org.InsuranceProductRelation;
import com.boot.org.service.InsuranceProductRelationService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/test")
public class TestController {

	@Reference(version="junbao-1.0.0")
	private InsuranceProductRelationService insuranceProductRelationService;

	
	@ResponseBody
	@RequestMapping(value="/bootCon")
	public Object getMessage(HttpServletRequest request,HttpServletResponse response,Integer id){
		List<InsuranceProductRelation> list = insuranceProductRelationService.relationList(id);
		List<Map<String, Object>>mapList = new ArrayList<Map<String,Object>>();
		Map<String, Object>map = null;
		for(InsuranceProductRelation lists:list){
			map = new HashMap<String,Object>();
			map.put("id", lists.getInsuranceProductRelationId());
			map.put("name", lists.getRelationName());
			mapList.add(map);
		}
		return JSONArray.fromObject(mapList).toString();
	}
}

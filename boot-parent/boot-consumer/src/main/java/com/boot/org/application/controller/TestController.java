package com.boot.org.application.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.org.service.HelloService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/test")
public class TestController {

//	@Reference(version="junbao-1.0.0")
//	private InsuranceProductRelationService insuranceProductRelationService;
	
	@Reference(version="junbao-1.0.0")
	private HelloService helloService;

	
	@ResponseBody
	@RequestMapping(value="/bootCon")
	public Object getMessage(HttpServletRequest request,HttpServletResponse response,Integer id){
		
		return helloService.sayHello("hello"+id);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/weixin")
	public void getMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
        String appid = "wx4b4009c4fce00e0c";
        String secret = "4d3aea976157935e563f8ef01c7a4293";
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        //第一次请求 获取access_token 和 openid
        String  oppid = new HttpRequestor().doGet(requestUrl);
        JSONObject oppidObj =JSONObject.fromObject(oppid);
        String access_token = (String) oppidObj.get("access_token");
        String openid = (String) oppidObj.get("openid");
        String requestUrl2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String userInfoStr = new HttpRequestor().doGet(requestUrl2);
        JSONObject wxUserInfo =JSONObject.fromObject(userInfoStr); 
	}
}

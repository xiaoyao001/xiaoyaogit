package com.boot.org.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.boot.org.alipay.AliPayConfig;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/alipay")
public class AlipayController {

	@ResponseBody
	@RequestMapping(value = "/dopay")
	public void payAction(HttpServletRequest request, HttpServletResponse response) {
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.ALIPAY_GATE_URL, AliPayConfig.APP_ID,
				AliPayConfig.MERCHANT_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET,
				AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.SIGNTYPE);
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("out_trade_no", System.currentTimeMillis());
		bodyJson.put("product_code", "FAST_INSTANT_TRADE_PAY");
		bodyJson.put("total_amount", "15000.00");
		bodyJson.put("subject", "苹果");
		bodyJson.put("body", "一个大苹果");
		//创建API对应的request
		AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
		//AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		payRequest.setReturnUrl(AliPayConfig.RETURN_URL+"alipay/returnUrl");
		payRequest.setNotifyUrl(AliPayConfig.NOTIFY_URL+"alipay/callback");
//		model.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
//		model.setTotalAmount("0.01");
//		model.setSubject("苹果");
		//payRequest.setBizModel(model);
		payRequest.setBizContent(bodyJson.toString());
		String form = null;
		try {
			form = alipayClient.pageExecute(payRequest).getBody(); //调用SDK生成表单
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(form);//直接将完整的表单html输出到页面
			response.getWriter().close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/callback")
	public void payActionCallBack(HttpServletRequest request, HttpServletResponse response,String id) {
		System.out.println("callback接口被触发"+id);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/returnUrl")
	public void returnUrl(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("returnUrl接口被触发");
	}
	

}

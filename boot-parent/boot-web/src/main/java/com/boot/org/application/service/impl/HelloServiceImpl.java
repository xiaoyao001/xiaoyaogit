package com.boot.org.application.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.org.service.HelloService;

@Service(interfaceClass=HelloService.class,version="junbao-1.0.0",timeout=10000)
@Component
@Transactional
public class HelloServiceImpl implements HelloService{

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("开启zookeeper调用:sayHello");
		return "（来自服务提供者）Hello:"+name;
	}

}

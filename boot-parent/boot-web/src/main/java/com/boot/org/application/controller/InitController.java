package com.boot.org.application.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope
@Controller
@RequestMapping(value="/bus")
public class InitController {

	
	@Autowired
	private ContextRefresher contextRefresher;
	
	
	@ResponseBody
	@RequestMapping(value="/refresh")
    public void refresh() {
        Set<String> set= contextRefresher.refresh();
        System.out.println(set);
    }
}

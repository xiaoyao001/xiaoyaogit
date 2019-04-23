package com.boot.org.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.org.application.config.DataSourceConfig;

@Controller
@RequestMapping(value="/bus")
public class InitController {

	
	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	
	@ResponseBody
	@RequestMapping(value="/refresh")
    public void refresh() {
        System.out.println(dataSourceConfig.getUrl());
    }
}

package com.boot.org.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.boot.org.application.config.DataSourceConfig;

@Controller
@RequestMapping(value = "/bus")
public class InitController {

	@Autowired
	private DataSourceConfig dataSourceConfig;
	@Autowired
	private WebApplicationContext context;

	@ResponseBody
	@RequestMapping(value = "/refresh")
	public void refresh(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(dataSourceConfig.getUrl());
	}
}

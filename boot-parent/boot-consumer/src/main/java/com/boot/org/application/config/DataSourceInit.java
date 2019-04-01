package com.boot.org.application.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.org.application.config.DataSourceConfig;
import com.boot.org.service.configcenter.DataSourceService;

/**
 * tomcat服务启动默认加载项
 * @author xiaoyao
 * @version 2019/3/30
 */

@Component
public class DataSourceInit implements ServletContextListener,ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Reference(version="junbao-1.0.0")
	private DataSourceService dataSourceService;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		DataSourceConfig.setUrl(dataSourceService.getConfigProperties().getDataSourceUrl());
		DataSourceConfig.setUsername(dataSourceService.getConfigProperties().getUserName());
		DataSourceConfig.setPassword(dataSourceService.getConfigProperties().getPassword());
		DataSourceConfig.setDriverClassName(dataSourceService.getConfigProperties().getDriver());
	}

}

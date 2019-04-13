package com.boot.org.application.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.org.common.ConfigCenter;
import com.boot.org.common.ResouceReadUtil;
import com.boot.org.service.configcenter.DataSourceService;

@Service(version="junbao-1.0.0",interfaceClass=DataSourceService.class,timeout=5000)
@Component
@Transactional
public class DataSourceServiceImpl implements DataSourceService{

	@Override
	public ConfigCenter getConfigProperties() {
		// TODO Auto-generated method stub
		ConfigCenter configCenter = new ConfigCenter(
				ResouceReadUtil.get("JUNBAO_URL"), 
				ResouceReadUtil.get("JUNABO_USERNAME"), 
				ResouceReadUtil.get("JUNABO_PASSWORD"), 
				ResouceReadUtil.get("JUNBAO_DRIVER"));
		return configCenter;
	}

}

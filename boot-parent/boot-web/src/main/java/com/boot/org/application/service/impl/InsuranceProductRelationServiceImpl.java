package com.boot.org.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.org.InsuranceProductRelation;
import com.boot.org.application.config.DataSourceConfig;
import com.boot.org.dao.InsuranceProductRelationMapper;
import com.boot.org.service.InsuranceProductRelationService;

@Service(interfaceClass=InsuranceProductRelationService.class,version="junbao-1.0.0",timeout=10000)//dubbo注解，与spring同名
@Component
@Transactional
public class InsuranceProductRelationServiceImpl implements InsuranceProductRelationService{

	@Autowired
	private InsuranceProductRelationMapper insuranceProductRelationMapper;
	
	@Autowired
	private  DataSourceConfig dataSourceConfig;

	@Override
	public List<InsuranceProductRelation> relationList(Integer id) {
		// TODO Auto-generated method stub
		System.out.println("开启zookeeper调用:relationList");
		return insuranceProductRelationMapper.relationList(id);
	}
	
	
}

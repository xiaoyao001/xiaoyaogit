package com.boot.org.application.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.boot.org.application.config.DataSourceConfig;
import com.boot.org.test.ResouceWriteUtil;

/**
 * tomcat服务启动默认加载项
 * 
 * @author xiaoyao
 * @version 2019/3/30
 */

@Component
public class DataSourceInit implements ServletContextListener, ApplicationContextAware {

	private static final String ZKURL = "localhost:2181";

	private ZkClient zkClient = new ZkClient(ZKURL, 1000, 1000, new SerializableSerializer());

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
		zkClient.setZkSerializer(new ZkSerializer() {
			@Override
			public byte[] serialize(Object data) throws ZkMarshallingError {
				try {
					return String.valueOf(data).getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			public Object deserialize(byte[] bytes) throws ZkMarshallingError {
				try {
					return new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					return null;
				}
			}
		});
		readZooData(zkClient);
		//System.out.println("初始化"+DataSourceConfig.getDriverClassName());
		IZkDataListener dataListener = new IZkDataListener() {
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("客户机监听到zookeeper改变");
				readZooData(zkClient);
				reloadZooData(zkClient);
			}
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub

			}
		};
		subscribeDataFunction(zkClient, dataListener);
	}
	
	
	
	private void readZooData(ZkClient zkClient){
		
		try {
			ResouceWriteUtil.writeResource(zkClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void reloadZooData(ZkClient zkClient){
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setUrl(zkClient.readData("/config/pre/datasource/url").toString());
		dataSourceConfig.setDriverClassName(zkClient.readData("/config/pre/datasource/driver").toString());
		dataSourceConfig.setUsername(zkClient.readData("/config/pre/datasource/username").toString());
		dataSourceConfig.setPassword(zkClient.readData("/config/pre/datasource/password").toString());	
	}
	
	
	private void subscribeDataFunction(ZkClient zkClient,IZkDataListener dataListener){
		zkClient.subscribeDataChanges("/config/pre/datasource/url", dataListener);
		zkClient.subscribeDataChanges("/config/pre/datasource/driver", dataListener);
		zkClient.subscribeDataChanges("/config/pre/datasource/username", dataListener);
		zkClient.subscribeDataChanges("/config/pre/datasource/password", dataListener);
	}
}

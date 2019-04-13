package com.boot.org.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;

/**
 * 写入properties文件util
 * @author xiaoyao
 * @version 2019.4.13
 */
public class ResouceWriteUtil {

	public static void writeResource(ZkClient zkClient) throws IOException{
		Properties prop = new Properties();   
		FileOutputStream oFile = new FileOutputStream("src/main/resources/application.properties",false);
		setValue(prop, zkClient);
		prop.store(oFile,null);
		oFile.close();
	}
	
	
	public static Properties setValue(Properties prop,ZkClient zkClient){
		prop.setProperty("spring.datasource.one.url", zkClient.readData("/config/pre/datasource/url").toString());
		prop.setProperty("spring.datasource.one.username", zkClient.readData("/config/pre/datasource/username").toString());
		prop.setProperty("spring.datasource.one.password", zkClient.readData("/config/pre/datasource/password").toString());
		prop.setProperty("spring.datasource.driver-class-name", zkClient.readData("/config/pre/datasource/driver").toString());
		prop.setProperty("spring.datasource.type", "com.alibaba.druid.pool.DruidDataSource");
		prop.setProperty("spring.datasource.initialSize", "3");
		prop.setProperty("spring.datasource.maxActive", "20");
		prop.setProperty("spring.datasource.removeAbandoned", "true");
		prop.setProperty("spring.datasource.removeAbandonedTimeout", "1800");
		prop.setProperty("spring.datasource.timeBetweenEvictionRunsMillis", "60000");
		prop.setProperty("spring.datasource.validationQuery", "SELECT 'x'");
		prop.setProperty("spring.datasource.testWhileIdle", "true");
		prop.setProperty("spring.datasource.testOnBorrow", "true");
		prop.setProperty("spring.datasource.testOnReturn", "true");
		prop.setProperty("spring.datasource.filters", "stat,wall,log4j");
		prop.setProperty("spring.datasource.connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500");
		prop.setProperty("spring.http.multipart.maxFileSize", "100Mb");
		prop.setProperty("spring.http.multipart.maxRequestSize", "1000Mb");
		prop.setProperty("server.port", "8080");
		prop.setProperty("spring.dubbo.appname", "boot-web");
		prop.setProperty("spring.dubbo.protocol", "dubbo");
		prop.setProperty("spring.dubbo.registry", "zookeeper://127.0.0.1:2181");
		prop.setProperty("spring.dubbo.port", "20900");
		return prop;
	}

}

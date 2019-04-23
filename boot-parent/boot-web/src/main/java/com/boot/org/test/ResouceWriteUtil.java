package com.boot.org.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.boot.org.application.config.DataSourceConfig;

/**
 * 写入properties文件util
 * @author xiaoyao
 * @version 2019.4.13
 */
@Component
public class ResouceWriteUtil implements ApplicationContextAware{

	
	private static ApplicationContext applicationContext;


	public void writeResource(ZkClient zkClient) throws IOException{
		Properties prop = new Properties();   
		FileOutputStream oFile = new FileOutputStream("src/main/resources/application.properties",false);
		setValue(prop, zkClient);
		prop.store(oFile,null);
		oFile.close();
	}
	
	
	public Properties setValue(Properties prop,ZkClient zkClient){
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
		prop.setProperty("server.port", "1010");
		prop.setProperty("spring.dubbo.appname", "boot-web");
		prop.setProperty("spring.dubbo.protocol", "dubbo");
		prop.setProperty("spring.dubbo.registry", "zookeeper://127.0.0.1:2181");
		prop.setProperty("spring.dubbo.port", "20900");
		DataSourceConfig.setConnectionProperties(prop);
		/*prop.setProperty("spring.cloud.config", "false");
		prop.setProperty("management.security.enabled", "false");*/
		Object object = getBean(DataSourceConfig.class);
		DataSourceConfig dataSourceConfig = (DataSourceConfig)object;
		dataSourceConfig.setUrl(zkClient.readData("/config/pre/datasource/url").toString());
		dataSourceConfig.setPassword(zkClient.readData("/config/pre/datasource/password").toString());
		dataSourceConfig.setUsername(zkClient.readData("/config/pre/datasource/username").toString());
		dataSourceConfig.setDriverClassName(zkClient.readData("/config/pre/datasource/driver").toString());
		dataSourceConfig.setInitialSize(3);
		dataSourceConfig.setMaxActive(20);
		dataSourceConfig.setRemoveAbandoned(true);
		dataSourceConfig.setRemoveAbandonedTimeout(1800);
		dataSourceConfig.setTimeBetweenEvictionRunsMillis(60000);
		dataSourceConfig.setValidationQuery("SELECT 'x'");
		dataSourceConfig.setTestWhileIdle(true);
		dataSourceConfig.setTestOnBorrow(true);
		dataSourceConfig.setTestOnReturn(true);
		dataSourceConfig.setFilters("stat,wall,log4j");
		ContextRefreshedEvent contextRefreshedEvent = new ContextRefreshedEvent(getApplicationContext());
		getApplicationContext().publishEvent(contextRefreshedEvent);
		return prop;
	}


	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		ResouceWriteUtil.applicationContext = applicationContext;
	}

	/**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
 
    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
 
    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}

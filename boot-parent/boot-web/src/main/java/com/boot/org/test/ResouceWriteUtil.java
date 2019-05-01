package com.boot.org.test;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.boot.org.application.config.DataSourceConfig;

/**
 * 写入properties文件util
 * 
 * @author xiaoyao
 * @version 2019.4.13
 */

@Component
public class ResouceWriteUtil implements ApplicationContextAware {
	

	private ApplicationContext applicationContext;

	public void writeResource(ZkClient zkClient) throws Exception {
		Properties prop = new Properties();
		FileOutputStream oFile = new FileOutputStream("src/main/resources/application.properties", false);
		setValue(prop, zkClient);
		prop.store(oFile, null);
		oFile.close();

	}

	public Properties setValue(Properties prop, ZkClient zkClient)
			throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		prop.setProperty("spring.datasource.one.url", zkClient.readData("/config/pre/datasource/url").toString());
		prop.setProperty("spring.datasource.one.username",
				zkClient.readData("/config/pre/datasource/username").toString());
		prop.setProperty("spring.datasource.one.password",
				zkClient.readData("/config/pre/datasource/password").toString());
		prop.setProperty("spring.datasource.driver-class-name",
				zkClient.readData("/config/pre/datasource/driver").toString());
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
		prop.setProperty("spring.datasource.connectionProperties",
				"druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500");
		prop.setProperty("spring.http.multipart.maxFileSize", "100Mb");
		prop.setProperty("spring.http.multipart.maxRequestSize", "1000Mb");
		prop.setProperty("server.port", "1010");
		prop.setProperty("spring.dubbo.appname", "boot-web");
		prop.setProperty("spring.dubbo.protocol", "dubbo");
		prop.setProperty("spring.dubbo.registry", "zookeeper://127.0.0.1:2181");
		prop.setProperty("spring.dubbo.port", "20900");
		DataSourceConfig.setConnectionProperties(prop);
		Object object = getBean(DataSourceConfig.class);// url
		getMethod(object, "setUrl", new String[] { zkClient.readData("/config/pre/datasource/url").toString() });
		getMethod(object, "setPassword",
				new String[] { zkClient.readData("/config/pre/datasource/password").toString() });
		getMethod(object, "setUsername",
				new String[] { zkClient.readData("/config/pre/datasource/username").toString() });
		getMethod(object, "setDriverClassName",
				new String[] { zkClient.readData("/config/pre/datasource/driver").toString() });
		getMethod(object, "setInitialSize", new Integer[] { 3 });
		getMethod(object, "setMaxActive", new Integer[] { 20 });
		getMethod(object, "setRemoveAbandoned", new Boolean[] { true });
		getMethod(object, "setRemoveAbandonedTimeout", new Integer[] { 1800 });
		getMethod(object, "setTimeBetweenEvictionRunsMillis", new Integer[] { 60000 });
		getMethod(object, "setValidationQuery", new String[] { "SELECT 'x'" });
		getMethod(object, "setTestWhileIdle", new Boolean[] { true });
		getMethod(object, "setTestOnBorrow", new Boolean[] { true });
		getMethod(object, "setTestOnReturn", new Boolean[] { true });
		getMethod(object, "setFilters", new String[] { "stat,wall,log4j" });
		getMethod(object, "setConnectionProperties", new Properties[] { prop });
		ContextRefreshedEvent contextRefreshedEvent = new ContextRefreshedEvent(applicationContext);
		applicationContext.publishEvent(contextRefreshedEvent);
		return prop;
	}

	@SuppressWarnings("rawtypes")
	public static Object getMethod(Object object, String methodName, Object[] dataArray) {
		try {
			Class<? extends Object> class1 = object.getClass();
			Class[] paramTypes = null;
			Method[] methods = object.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				// 和传入方法名匹配
				if (methodName.equals(methods[i].getName())) {
					Class[] params = methods[i].getParameterTypes();
					paramTypes = new Class[params.length];
					for (int j = 0; j < params.length; j++) {
						paramTypes[j] = Class.forName(params[j].getName());
					}
					break;
				}
			}
			Method method = class1.getDeclaredMethod(methodName, paramTypes);
			Object result = method.invoke(object, dataArray);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(object + "对象-" + methodName + "函数异常-异常信息为：" + e);
			return null;
		}
	}

	/**
	 * 通过name获取 Bean.
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 通过class获取Bean.
	 * 
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * 
	 * @param name
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

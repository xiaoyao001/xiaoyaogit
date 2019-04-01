package com.boot.org.common;

import java.io.Serializable;

/**
 * 配置中心实体
 * @author xiaoyao
 * @version 2019-3-27
 */
public class ConfigCenter implements Serializable{

	private static final long serialVersionUID = -4439685133464238463L;
	/**数据源连接地址*/
	private String dataSourceUrl;
	/**数据源用户名*/
	private String userName;
	/**数据源密码*/
	private String password;
	/**数据源驱动*/
	private String driver;
	
	public ConfigCenter(){}

	public ConfigCenter(String dataSourceUrl, String userName, String password, String driver) {
		this.dataSourceUrl = dataSourceUrl;
		this.userName = userName;
		this.password = password;
		this.driver = driver;
	}

	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	
}

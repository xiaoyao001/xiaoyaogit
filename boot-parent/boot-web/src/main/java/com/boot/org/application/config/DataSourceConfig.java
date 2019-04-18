package com.boot.org.application.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

@Component
@ConfigurationProperties(prefix="application.properties")
@MapperScan(basePackages = DataSourceConfig.DAOPACKAGE, 
sqlSessionFactoryRef = "defaultSqlSessionFactory")
//@RefreshScope
public class DataSourceConfig{
	
	
	private static DataSourceConfig INSTANCE;
	
	
	public DataSourceConfig(){}
	
	
	public DataSourceConfig getInstance(){
		if(INSTANCE == null){
			synchronized (DataSourceConfig.class) {
				if(INSTANCE == null){
					INSTANCE = new DataSourceConfig();
				}
			}
		}
		return INSTANCE;
	}
	

	
	// 扫描dao包
    static final String DAOPACKAGE = "com.boot.org.dao";

    @Value("${spring.datasource.one.url}")
    private String url;
    
	@Value("${spring.datasource.one.username}")
	private String username;
	
    @Value("${spring.datasource.one.password}")
    private String password;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName = "";

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.removeAbandoned}")
    private boolean removeAbandoned;

    @Value("${spring.datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${spring.datasource.connectionProperties}")
    private Properties connectionProperties;
    
    @Bean(name = "dataSource")
    @Primary
    @RefreshScope
    public DataSource getDataSource() {
    	System.out.println("热加载数据源配置");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        // druid配置
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setRemoveAbandoned(removeAbandoned);
        druidDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        try {
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }
        druidDataSource.setConnectProperties(connectionProperties);
        return druidDataSource;
    }
    
    @Bean(name = "defaultSqlSessionFactory")
    @Primary
    @RefreshScope
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mappers/*.xml"));
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Bean(name = "defaultSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate defaultSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager defaultAnnotationDrivenTransactionManager(){
        return new DataSourceTransactionManager(getDataSource());
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
    
}

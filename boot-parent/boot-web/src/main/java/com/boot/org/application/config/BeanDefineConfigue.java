package com.boot.org.application.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		         ConfigurableApplicationContext context = (ConfigurableApplicationContext) event.getApplicationContext();
		         DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
		         // 控制器
		         String[] controllers = factory.getBeanNamesForAnnotation(Configuration.class);
		         if(controllers != null) {
		             for(String controllerBeanName : controllers) {
		                 BeanDefinition beanDefine = factory.getBeanDefinition(controllerBeanName);
		                 String scope = beanDefine.getScope();
		                 //if(scope == null || !scope.equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE)) {
		                     beanDefine.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
		                     factory.registerBeanDefinition(controllerBeanName, beanDefine);
		                 //}
		             }
		         }
		         // 校验器
		         Object[] validators = factory.getBeanNamesForType(DataSourceConfig.class);
		         if(validators != null) {
		             for(Object _validatorBeanName : validators) {
		                 String validatorBeanName = String.valueOf(_validatorBeanName);
		                 BeanDefinition beanDefine = factory.getBeanDefinition(validatorBeanName);
		                 String scope = beanDefine.getScope();
		                 //if(scope == null || !scope.equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE)) {
		                     beanDefine.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
		                     BeanDefinitionBuilder.genericBeanDefinition(DataSourceConfig.class);
		                     factory.registerBeanDefinition(validatorBeanName, beanDefine);
		                 //}
		             }
		         }
		         DataSourceConfig dataSourceConfig = (DataSourceConfig) event.getApplicationContext().getBean("dataSourceConfig");
		         
		     }

	
	@EventListener
	public void contextRefreshListener(ContextRefreshedEvent event){
		System.out.println("我进入了监听器");
		onApplicationEvent(event);
	}
}

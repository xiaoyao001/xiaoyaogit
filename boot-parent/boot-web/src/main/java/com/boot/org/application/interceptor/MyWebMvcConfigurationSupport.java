package com.boot.org.application.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/bus/**");
		super.addInterceptors(registry);
	}

	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/");
		super.addResourceHandlers(registry);
	}

	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		super.addViewControllers(registry);
	}
}

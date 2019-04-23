package com.boot.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

//dubbo提供者
@SpringBootApplication
@MapperScan("com.boot.org.dao")
@EnableDubboConfiguration
public class MyApplication extends SpringBootServletInitializer implements CommandLineRunner {

	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MyApplication.class, args);
	}

	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyApplication.class);
    }
	
	@Override
	public void run(String... arg0){
		// TODO Auto-generated method stub
		System.out.println("dubbo提供者--启动完成！");
		
	}
}

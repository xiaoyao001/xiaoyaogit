package com.boot.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

//dubbo消费者
@SpringBootApplication
/*@MapperScan("com.boot.org.dao")*/
@EnableDubboConfiguration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MyApplication{


	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
		System.out.println("dubbo消费者--启动完成！");
	}
}

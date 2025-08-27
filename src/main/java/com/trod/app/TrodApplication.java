package com.trod.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.trod")
@MapperScan("com.trod.mapper")
@EntityScan("com.trod.entity")
public class TrodApplication  {

	public static void main(String[] args) {
		// 讀取 .env 檔案時使用
//		DotenvPostProcessor.loadEnv();

		SpringApplication.run(TrodApplication.class, args);
	}
}

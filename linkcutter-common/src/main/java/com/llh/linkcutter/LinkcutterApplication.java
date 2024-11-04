package com.llh.linkcutter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.llh"})  // 扫描 com.llh 和 com.linkcutter
public class LinkcutterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkcutterApplication.class, args);
	}

}

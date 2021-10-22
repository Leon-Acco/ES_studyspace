package com.acho.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.acho.dao")
public class SampleEs01Application {

    public static void main(String[] args) {
        SpringApplication.run(SampleEs01Application.class, args);
    }

}

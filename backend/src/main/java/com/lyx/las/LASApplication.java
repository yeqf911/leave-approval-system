package com.lyx.las;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.lyx.las")
public class LASApplication {

    public static void main(String[] args) {
        SpringApplication.run(LASApplication.class, args);
    }

}

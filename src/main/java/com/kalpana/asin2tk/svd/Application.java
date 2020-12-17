package com.kalpana.asin2tk.svd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author 2tk
 * @version 1.0
 * @date 2020/8/10 13:58
 **/
@SpringBootApplication
@EnableEurekaClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}

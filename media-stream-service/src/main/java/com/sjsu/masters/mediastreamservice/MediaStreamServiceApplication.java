package com.sjsu.masters.mediastreamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MediaStreamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaStreamServiceApplication.class, args);
    }

}

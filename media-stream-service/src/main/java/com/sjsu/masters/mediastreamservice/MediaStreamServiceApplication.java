package com.sjsu.masters.mediastreamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.*;
import org.springframework.cloud.openfeign.*;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MediaStreamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaStreamServiceApplication.class, args);
    }

}

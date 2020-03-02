package com.sjsu.masters.loadpredictionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoadPredictionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadPredictionServiceApplication.class, args);
    }

}

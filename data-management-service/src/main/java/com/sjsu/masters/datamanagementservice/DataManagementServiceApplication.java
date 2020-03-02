package com.sjsu.masters.datamanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataManagementServiceApplication.class, args);
    }

}

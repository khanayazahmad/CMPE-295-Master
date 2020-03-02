package com.sjsu.masters.containermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ContainerManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerManagementServiceApplication.class, args);
    }

}

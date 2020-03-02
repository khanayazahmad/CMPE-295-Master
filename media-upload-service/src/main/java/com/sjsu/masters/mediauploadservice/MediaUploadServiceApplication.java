package com.sjsu.masters.mediauploadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MediaUploadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaUploadServiceApplication.class, args);
    }

}

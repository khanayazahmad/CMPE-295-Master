package com.sjsu.masters.mediatranscodingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MediaTranscodingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaTranscodingServiceApplication.class, args);
    }

}

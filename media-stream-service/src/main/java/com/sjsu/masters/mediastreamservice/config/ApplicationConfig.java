package com.sjsu.masters.mediastreamservice.config;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Slf4j
@Configuration
public class ApplicationConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
                                   @Value("${cloud.aws.region.static}") String region) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }


}
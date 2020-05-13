package com.sjsu.masters.mediauploadservice.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

import static com.amazonaws.services.s3.internal.Constants.MB;

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

    @Bean
    public TransferManager transferManager(AWSCredentialsProvider credentialsProvider,
                                           @Value("${cloud.aws.region.static}") String region){
        return TransferManagerBuilder.standard()
                .withS3Client(amazonS3Client(credentialsProvider, region))
                .withDisableParallelDownloads(false)
                .withMinimumUploadPartSize(Long.valueOf(5 * MB))
                .withMultipartUploadThreshold(Long.valueOf(16 * MB))
                .withMultipartCopyPartSize(Long.valueOf(5 * MB))
                .withMultipartCopyThreshold(Long.valueOf(100 * MB))
                .withExecutorFactory(()->createExecutorService(20))
                .build();
    }

    private ThreadPoolExecutor createExecutorService(int threadNumber) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int threadCount = 1;

            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("vpc-amazon-s3-transfer-manager-worker-" + threadCount++);
                return thread;
            }
        };
        return (ThreadPoolExecutor)Executors.newFixedThreadPool(threadNumber, threadFactory);
    }
}
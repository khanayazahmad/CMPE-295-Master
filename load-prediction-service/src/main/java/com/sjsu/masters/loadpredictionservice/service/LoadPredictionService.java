package com.sjsu.masters.loadpredictionservice.service;


import com.sjsu.masters.loadpredictionservice.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Service
public class LoadPredictionService {
    private List<Metrics> metrics;
    private final RestTemplate restTemplate;
    public LoadPredictionService(RestTemplateBuilder restTemplateBuilder, List<Metrics> metrics){
        this.restTemplate = restTemplateBuilder.build();
        this.metrics = metrics;
    }

    public Prediction getPrediction (Integer microserviceId, Long startTime, Long endTime) throws IOException {
        String url = ("http://ec2-18-214-191-237.compute-1.amazonaws.com:8080/metrics/{microserviceId}?startTime="+String.valueOf(startTime)+"&endTime="+String.valueOf(endTime));
        metrics = Arrays.asList(this.restTemplate.getForEntity(url, Metrics[].class, microserviceId).getBody());
        Metrics metric = metrics.get(metrics.size() - 1);
        ProcessBuilder loadPrediction = new ProcessBuilder(
                "python","LoadPrediction_predict.py",
                ""+metric.getCpuUsage(),""+metric.getMemoryUsage(),
                ""+metric.getNumberOfPods(),""+metric.getLatency(),
                ""+metric.getBandwidth());
        loadPrediction.redirectErrorStream(true);
        Process lp = loadPrediction.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(lp.getInputStream()));
        int result = Integer.parseInt(in.readLine());
        Prediction prediction = new Prediction(microserviceId, result);
        return prediction;
    }

    public String updateModel(UpdateInfo update) throws IOException {
        Integer microserviceId = update.getMicroserviceId();
        Long timestamp = update.getTimestamp();
        Integer action = update.getAction();
        ProcessBuilder modelUpdate = new ProcessBuilder(
                "python","LoadPrediction_update.py",""+microserviceId,""+timestamp,""+action);
        modelUpdate.redirectErrorStream(true);
        Process mu = modelUpdate.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(mu.getInputStream()));
        return(in.readLine().toString());

    }

}




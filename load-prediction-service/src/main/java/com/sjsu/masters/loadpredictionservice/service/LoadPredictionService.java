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
    private List<UpdateInfo> updates;
    private final RestTemplate restTemplate;


    public LoadPredictionService(RestTemplateBuilder restTemplateBuilder, List<Metrics> metrics, List<UpdateInfo> updates){
        this.restTemplate = restTemplateBuilder.build();
        this.metrics = metrics;
        this.updates = updates;
    }

    public Prediction getPrediction (Integer microserviceId, Long startTime, Long endTime) throws IOException {
        String url = ("http://a7fac3082e07246d58f635932018ad79-586489182.us-east-1.elb.amazonaws.com:8802/metrics/{microserviceId}?startTime="+String.valueOf(startTime)+"&endTime="+String.valueOf(endTime));
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
        String url = ("http://a7fac3082e07246d58f635932018ad79-586489182.us-east-1.elb.amazonaws.com:8802/metrics/{microserviceId}?startTime="+String.valueOf(timestamp)+"&endTime="+String.valueOf(timestamp+1));
        metrics = Arrays.asList(this.restTemplate.getForEntity(url, Metrics[].class, microserviceId).getBody());
        Metrics old_state = metrics.get(metrics.size() - 1);

        String url2 = ("http://a7fac3082e07246d58f635932018ad79-586489182.us-east-1.elb.amazonaws.com:8802/metrics/{microserviceId}?startTime="+String.valueOf(timestamp)+"&endTime="+String.valueOf(timestamp+500000));
        metrics = Arrays.asList(this.restTemplate.getForEntity(url2, Metrics[].class, microserviceId).getBody());
        Metrics new_state = metrics.get(metrics.size() - 1);

        String url3 = ("http://a7fac3082e07246d58f635932018ad79-586489182.us-east-1.elb.amazonaws.com:8802/predictions/0?startTime="+String.valueOf(timestamp)+"&endTime="+String.valueOf(timestamp+500000));
        updates = Arrays.asList(this.restTemplate.getForEntity(url3, UpdateInfo[].class).getBody());
        UpdateInfo updateinfo = updates.get(updates.size() - 1);
        ProcessBuilder modelUpdate = new ProcessBuilder(
                "python","LoadPrediction_update.py",
                ""+old_state.getCpuUsage(),""+old_state.getMemoryUsage(),
                ""+old_state.getNumberOfPods(),""+old_state.getLatency(),
                ""+old_state.getBandwidth(),""+new_state.getCpuUsage(),""+new_state.getMemoryUsage(),
                ""+new_state.getNumberOfPods(),""+new_state.getLatency(),
                ""+new_state.getBandwidth(),""+updateinfo.getPredictionResult());
        modelUpdate.redirectErrorStream(true);
        Process mu = modelUpdate.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(mu.getInputStream()));
        return(in.readLine().toString());

    }

}




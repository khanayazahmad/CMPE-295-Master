package com.sjsu.masters.loadpredictionservice;

import com.sjsu.masters.loadpredictionservice.model.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class LoadPredictionService {

    public LoadPredictionService(){

    }

    public Prediction getPrediction (LoadPredictionRequest request) throws IOException {
        Integer microserviceId = request.getMicroserviceId();
        Long timestamp = request.getTimestamp();
        ProcessBuilder loadPrediction = new ProcessBuilder(
                "python","LoadPrediction_predict.py",""+microserviceId,""+timestamp);
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




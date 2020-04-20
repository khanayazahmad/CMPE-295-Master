package com.sjsu.masters.loadpredictionservice.model;

public class Prediction {
    private Integer microserviceId;
    private Integer predictionResult;

    public Prediction(Integer microserviceId, Integer predictionResult){
        this.microserviceId = microserviceId;
        this.predictionResult = predictionResult;
    }

    public Integer getMicroserviceId() { return microserviceId; }
    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Integer getPredictionResult() { return predictionResult; }
    public void setPredictionResult(Integer predictionResult) { this.predictionResult = predictionResult; }
}


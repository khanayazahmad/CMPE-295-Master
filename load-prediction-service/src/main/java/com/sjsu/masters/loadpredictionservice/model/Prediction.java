package com.sjsu.masters.loadpredictionservice.model;

public class Prediction {
    private Integer microserviceId;
    private Integer LPSresult;

    public Prediction(Integer microserviceId, Integer predictionResult){
        this.microserviceId = microserviceId;
        this.LPSresult = predictionResult;
    }

    public Integer getMicroserviceId() { return microserviceId; }
    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Integer getPredictionResult() { return LPSresult; }
    public void setPredictionResult(Integer predictionResult) { this.LPSresult = predictionResult; }
}


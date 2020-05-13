package com.sjsu.masters.loadpredictionservice.model;

public class UpdateInfo {
    private Integer microserviceId;
    private Long timestamp;
    private Integer predictionResult;

    public UpdateInfo(Integer microserviceId, Long timestamp, Integer action){
        this.microserviceId = microserviceId;
        this.timestamp = timestamp;
        this.predictionResult = action;
    }

    public Integer getMicroserviceId() { return microserviceId; }
    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public Integer getPredictionResult() { return predictionResult; }
    public void setAction(Integer action) { this.predictionResult = action; }
}

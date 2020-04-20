package com.sjsu.masters.loadpredictionservice.model;

public class LoadPredictionRequest {
    private Integer microserviceId;
    private Long timestamp;

    public LoadPredictionRequest(Integer microserviceId, Long timestamp) {
        this.microserviceId = microserviceId;
        this.timestamp = timestamp;

    }

    public Integer getMicroserviceId() { return microserviceId; }

    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

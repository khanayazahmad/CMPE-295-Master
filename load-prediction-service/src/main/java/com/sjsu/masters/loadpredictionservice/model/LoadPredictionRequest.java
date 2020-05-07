package com.sjsu.masters.loadpredictionservice.model;

public class LoadPredictionRequest {
    private Integer microserviceId;
    private Long startTime;
    private Long endTime;

    public LoadPredictionRequest(Integer microserviceId, Long startTime, Long endTime) {
        this.microserviceId = microserviceId;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public Integer getMicroserviceId() { return microserviceId; }
    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Long getStartTime() { return startTime; }
    public void setStartTime(Long timestamp) { this.startTime = startTime; }

    public Long getEndTime() { return endTime; }
    public void setEndTime(Long endTime) { this.endTime = endTime; }
}

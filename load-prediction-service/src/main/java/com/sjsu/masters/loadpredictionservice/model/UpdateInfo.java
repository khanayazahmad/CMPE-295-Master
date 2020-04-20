package com.sjsu.masters.loadpredictionservice.model;

public class UpdateInfo {
    private Integer microserviceId;
    private Long timestamp;
    private Integer action;

    public UpdateInfo(Integer microserviceId, Long timestamp, Integer action){
        this.microserviceId = microserviceId;
        this.timestamp = timestamp;
        this.action = action;
    }

    public Integer getMicroserviceId() { return microserviceId; }
    public void setMicroserviceId(Integer microserviceId) { this.microserviceId = microserviceId; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public Integer getAction() { return action; }
    public void setAction(Integer action) { this.action = action; }
}

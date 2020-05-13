package com.sjsu.masters.loadpredictionservice.model;

public class Metrics {

    private Long timestamp;
    private Integer microserviceId;
    private Integer memoryUsage;
    private Integer cpuUsage;
    private Integer numberOfRequests;
    private Integer numberOfPods;
    private Integer latency;
    private Integer bandwidth;

    public Metrics(
            Long timestamp,
            Integer microserviceId,
            Integer memoryUsage,
            Integer cpuUsage,
            Integer numberOfRequests,
            Integer numberOfPods,
            Integer latency,
            Integer bandwidth
    ) {
        this.timestamp = timestamp;
        this.microserviceId = microserviceId;
        this.memoryUsage = memoryUsage;
        this.cpuUsage = cpuUsage;
        this.numberOfRequests = numberOfRequests;
        this.numberOfPods = numberOfPods;
        this.latency = latency;
        this.bandwidth = bandwidth;
    }

    public Metrics() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMicroserviceId() {
        return microserviceId;
    }

    public void setMicroserviceId(Integer microserviceId) {
        this.microserviceId = microserviceId;
    }

    public Integer getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Integer memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Integer getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Integer cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Integer getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(Integer numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public Integer getNumberOfPods() {
        return numberOfPods;
    }

    public void setNumberOfPods(Integer numberOfPods) {
        this.numberOfPods = numberOfPods;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }
}

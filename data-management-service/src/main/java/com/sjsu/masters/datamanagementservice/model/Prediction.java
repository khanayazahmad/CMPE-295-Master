package com.sjsu.masters.datamanagementservice.model;

public class Prediction {
    private Long timestamp;
    private Integer microserviceId;
    private Integer predictionResult;

    public Prediction(
            Long timestamp,
            Integer microserviceId,
            Integer predictionResult
    ){
        this.timestamp = timestamp;
        this.microserviceId = microserviceId;
        this.predictionResult = predictionResult;
    }

    public Prediction(){};

    public Long getTimestamp(){return timestamp; }
    public void setTimestamp(Long timestamp){this.timestamp=timestamp;}

    public Integer getMicroserviceId(){return microserviceId;}
    public void setMicroserviceId(Integer microserviceId){this.microserviceId=microserviceId;}

    public Integer getPredictionResult() {return predictionResult;}
    public void setPredictionResult(Integer predictionResult){this.predictionResult=predictionResult;}
}

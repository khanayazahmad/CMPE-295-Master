package com.sjsu.masters.datamanagementservice.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "prediction_by_microservice")
public class PredictionByMicroservice extends Prediction{

    public PredictionByMicroservice(){
    };

    public PredictionByMicroservice(
            Long timestamp,
            Integer microserviceId,
            Integer predictionResult
    ) {
        super(
                timestamp,
                microserviceId,
                predictionResult
        );
    }

    public PredictionByMicroservice(Prediction prediction){
        super(
                prediction.getTimestamp(),
                prediction.getMicroserviceId(),
                prediction.getPredictionResult()
        );
    }

    @PrimaryKeyColumn(ordinal =  0, type = PrimaryKeyType.PARTITIONED)
    public Integer getMicroserviceId() {
        return super.getMicroserviceId();
    }

    @PrimaryKeyColumn(ordinal =  1, type = PrimaryKeyType.CLUSTERED)
    public Long getTimestamp() {
        return super.getTimestamp();
    }

}

package com.sjsu.masters.datamanagementservice.service;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.repository.cassandra.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {
    private PredictionByMicroserviceRepo predictionByMicroserviceRepo;

    @Autowired
    public PredictionService (PredictionByMicroserviceRepo predictionByMicroserviceRepo){
        this.predictionByMicroserviceRepo = predictionByMicroserviceRepo;
    }
    public boolean save(Prediction prediction){
        if( predictionByMicroserviceRepo.save(new PredictionByMicroservice(prediction)) != null) {
            predictionByMicroserviceRepo.save(new PredictionByMicroservice(prediction));
            return true;
        }
        return false;
    }

    public List<Prediction> findByMicroserviceAndTimeRange(Integer microserviceId, Long startTime, Long endTime){
        List<PredictionByMicroservice> predictionByMicroserviceList =
                predictionByMicroserviceRepo.findByMicroserviceIdAndTimestampGreaterThanEqualAndTimestampLessThan(microserviceId, startTime, endTime);
        return predictionByMicroserviceList.stream().map(predictionByMicroservice -> (Prediction) predictionByMicroservice).collect(Collectors.toList());
    }

}

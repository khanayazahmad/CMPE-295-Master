package com.sjsu.masters.datamanagementservice.service;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.repository.cassandra.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class MetricsService {

    private MetricsByMicroserviceRepo metricsByMicroserviceRepo;
    private MetricsByTimestampRepo metricsByTimestampRepo;

    @Autowired
    public MetricsService(MetricsByMicroserviceRepo metricsByMicroserviceRepo, MetricsByTimestampRepo metricsByTimestampRepo) {
        this.metricsByMicroserviceRepo = metricsByMicroserviceRepo;
        this.metricsByTimestampRepo = metricsByTimestampRepo;
    }

    public boolean save(Metrics metrics){
       if( metricsByMicroserviceRepo.save(new MetricsByMicroservice(metrics)) != null) {
           metricsByTimestampRepo.save(new MetricsByMicroservice(metrics));
           return true;
       }
       return false;
    }

    public List<Metrics> findByMicroserviceAndTimeRange(Integer microserviceId, Long startTime, Long endTime){
        List<MetricsByMicroservice> metricsByMicroserviceList =
                metricsByMicroserviceRepo.findByMicroserviceIdAndTimestampGreaterThanEqualAndTimestampLessThan(microserviceId, startTime, endTime);
        return metricsByMicroserviceList.stream().map(metricsByMicroservice -> (Metrics) metricsByMicroservice).collect(Collectors.toList());
    }
}

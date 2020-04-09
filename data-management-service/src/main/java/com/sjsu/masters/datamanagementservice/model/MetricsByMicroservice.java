package com.sjsu.masters.datamanagementservice.model;


import org.springframework.data.cassandra.core.cql.*;
import org.springframework.data.cassandra.core.mapping.*;

@Table(value = "metrics_by_microservice")
public class MetricsByMicroservice extends Metrics{

    public MetricsByMicroservice() {
    }

    public MetricsByMicroservice(
            Long timestamp,
            Integer microserviceId,
            Integer memoryUsage,
            Integer cpuUsage,
            Integer numberOfRequests,
            Integer numberOfPods,
            Integer latency,
            Integer bandwidth
    ) {
        super(
                timestamp,
                microserviceId,
                memoryUsage,
                cpuUsage,
                numberOfRequests,
                numberOfPods,
                latency,
                bandwidth
        );
    }

    public MetricsByMicroservice(Metrics metrics){
        super(
                metrics.getTimestamp(),
                metrics.getMicroserviceId(),
                metrics.getMemoryUsage(),
                metrics.getCpuUsage(),
                metrics.getNumberOfRequests(),
                metrics.getNumberOfPods(),
                metrics.getLatency(),
                metrics.getBandwidth()
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

package com.sjsu.masters.datamanagementservice.repository.cassandra;

import com.sjsu.masters.datamanagementservice.model.*;
import org.springframework.data.cassandra.repository.*;
import org.springframework.data.cassandra.repository.support.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface MetricsByMicroserviceRepo extends CassandraRepository<MetricsByMicroservice, Integer> {

    List<MetricsByMicroservice> findByMicroserviceIdAndTimestampGreaterThanEqualAndTimestampLessThan(
            Integer microserviceId, Long startTime, Long endTime
    );

}

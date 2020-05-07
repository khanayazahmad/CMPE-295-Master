package com.sjsu.masters.datamanagementservice.repository.cassandra;

import com.sjsu.masters.datamanagementservice.model.*;
import org.springframework.data.cassandra.repository.*;
import org.springframework.data.cassandra.repository.support.*;
import org.springframework.stereotype.*;

import java.util.List;


@Repository
public interface PredictionByMicroserviceRepo extends CassandraRepository<PredictionByMicroservice, Integer> {

    List<PredictionByMicroservice> findByMicroserviceIdAndTimestampGreaterThanEqualAndTimestampLessThan(
            Integer microserviceId, Long startTime, Long endTime);
}

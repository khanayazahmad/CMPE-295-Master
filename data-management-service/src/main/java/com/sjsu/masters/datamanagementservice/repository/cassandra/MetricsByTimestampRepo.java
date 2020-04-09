package com.sjsu.masters.datamanagementservice.repository.cassandra;

import com.sjsu.masters.datamanagementservice.model.*;
import org.springframework.data.cassandra.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface MetricsByTimestampRepo extends CassandraRepository<MetricsByMicroservice, Long> {

}

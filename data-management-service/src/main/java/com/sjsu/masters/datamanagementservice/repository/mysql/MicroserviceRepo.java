package com.sjsu.masters.datamanagementservice.repository.mysql;

import com.sjsu.masters.datamanagementservice.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepo extends CrudRepository<Microservice, Integer> {

}

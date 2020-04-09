package com.sjsu.masters.datamanagementservice.service;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.repository.mysql.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class MicroserviceService {

    private MicroserviceRepo microserviceRepo;

    @Autowired
    public MicroserviceService(MicroserviceRepo microserviceRepo) {
        this.microserviceRepo = microserviceRepo;
    }

    public boolean save(Microservice microservice){
        return microserviceRepo.save(microservice) == null? false : true;
    }

    public Microservice findById(Integer id){
        Optional<Microservice> result = microserviceRepo.findById(id);
        return result.isEmpty()? null : result.get();
    }

    public List<Microservice> findAll(){
        List<Microservice> microservices = new ArrayList<>();
        microserviceRepo.findAll().forEach(microservice -> microservices.add(microservice));
        return microservices;
    }

}

package com.sjsu.masters.datamanagementservice.controller;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/microservice")
public class MicroservicesController {

    private MicroserviceService microserviceService;

    @Autowired
    public MicroservicesController(MicroserviceService microserviceService) {
        this.microserviceService = microserviceService;
    }

    @RequestMapping(method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity<String> postMetrics(@RequestBody RequestWrapper<Microservice> request){
        if(microserviceService.save(request.getBody())){
            return ResponseEntity.ok("SUCCESS");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("ERROR");
        }
    }


    @RequestMapping(method= RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<Microservice>> getByAccessURL(){
        List<Microservice> microserviceList = microserviceService.findAll();
        if(microserviceList != null && !microserviceList.isEmpty()){
            return ResponseEntity.ok(microserviceList);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);

        }
    }

}

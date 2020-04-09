package com.sjsu.masters.datamanagementservice.controller;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/metrics")
public class MetricsController {

    private MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @RequestMapping(method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity<String> postMetrics(@RequestBody RequestWrapper<Metrics> request){
        if(metricsService.save(request.getBody())){
            return ResponseEntity.ok("SUCCESS");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("ERROR");
        }
    }

    @RequestMapping(method= RequestMethod.GET, value = "/{microserviceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Metrics>> getByAccessURL(
            @PathVariable                  Integer microserviceId,
            @RequestParam(required = true) Long startTime,
            @RequestParam(required = true) Long endTime
    ){
        List<Metrics> metricsList = metricsService.findByMicroserviceAndTimeRange(
                microserviceId, startTime, endTime
        );
        if(metricsList != null && !metricsList.isEmpty()){
            return ResponseEntity.ok(metricsList);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        }
    }
}

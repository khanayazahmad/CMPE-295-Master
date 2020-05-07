package com.sjsu.masters.datamanagementservice.controller;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/predictions")
public class PredictionController {

    private PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @RequestMapping(method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity<String> postPredictions(@RequestBody RequestWrapper<Prediction> request){
        if(predictionService.save(request.getBody())){
            return ResponseEntity.ok("SUCCESS");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("ERROR");
        }
    }

    @RequestMapping(method= RequestMethod.GET, value = "/{microserviceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Prediction>> getByAccessURL(
            @PathVariable                  Integer microserviceId,
            @RequestParam(required = true) Long startTime,
            @RequestParam(required = true) Long endTime
    ){
        List<Prediction> predictionList = predictionService.findByMicroserviceAndTimeRange(
                microserviceId, startTime, endTime
        );
        if(predictionList != null && !predictionList.isEmpty()){
            return ResponseEntity.ok(predictionList);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        }
    }

}

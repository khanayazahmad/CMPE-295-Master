package com.sjsu.masters.loadpredictionservice.controller;

import com.sjsu.masters.loadpredictionservice.model.*;
import com.sjsu.masters.loadpredictionservice.service.*;
import com.sjsu.masters.loadpredictionservice.model.UpdateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping(value = "/loadprediction")
public class LoadPredictionController {

    private LoadPredictionService loadPredictionService;

    @Autowired
    public LoadPredictionController(LoadPredictionService loadPredictionService){
        this.loadPredictionService = loadPredictionService;
    }

    @RequestMapping(method= RequestMethod.GET, value = "/{microserviceId}")
    public ResponseEntity<Prediction> doPrediction(
            @PathVariable                  Integer microserviceId,
            @RequestParam(required = true) Long startTime,
            @RequestParam(required = true) Long endTime
    ) throws IOException {
        Prediction prediction = loadPredictionService.getPrediction(microserviceId, startTime, endTime);
        return ResponseEntity.ok(prediction);
    }

    @RequestMapping(method= RequestMethod.PUT, value = "/updates")
    public ResponseEntity<String> updateModel(@RequestBody UpdateInfo update) throws IOException {
        String status = loadPredictionService.updateModel(update);
        return ResponseEntity.ok(status);
    }

    /*
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> updateModel(@RequestBody UpdateInfo update) throws IOException {
        String status = loadPredictionService.updateModel(update);
        return ResponseEntity.ok(status);
    }
     */

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<String> ping(){
        return ResponseEntity.ok("This the health check of Load Prediction Request");
    }

}

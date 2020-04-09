package com.sjsu.masters.datamanagementservice.controller;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/video-metadata")
public class VideoMetadataController {

    private VideoMetadataService videoMetadataService;

    @Autowired
    public VideoMetadataController(VideoMetadataService videoMetadataService) {
        this.videoMetadataService = videoMetadataService;
    }

    @RequestMapping(method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseEntity<String> createAll(@RequestBody RequestWrapper<List<VideoMetadata>> request){
        if(videoMetadataService.saveAll(request.getBody())){
            return ResponseEntity.ok("SUCCESS");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("ERROR");
        }
    }

    @RequestMapping(method= RequestMethod.GET, value = "/{accessUrl}", produces = "application/json")
    public @ResponseBody ResponseEntity<VideoMetadata> getByAccessURL(@PathVariable(value = "accessUrl") String accessUrl){
        VideoMetadata metadata = videoMetadataService.getVideoMetadataByAccessURL(accessUrl);
        if(metadata != null){
            return ResponseEntity.ok(metadata);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);

        }
    }

    @RequestMapping(method= RequestMethod.GET, value = "/owner/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<VideoMetadata>> getByOwner(@PathVariable(value = "id") String id){
        List<VideoMetadata> metadataList = videoMetadataService.getVideoMetadataByOwner(id);
        if(metadataList != null && !metadataList.isEmpty()){
            return ResponseEntity.ok(metadataList);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(null);
        }
    }

}

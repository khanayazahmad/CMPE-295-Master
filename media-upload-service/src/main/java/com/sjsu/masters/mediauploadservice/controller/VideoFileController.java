package com.sjsu.masters.mediauploadservice.controller;

import com.sjsu.masters.mediauploadservice.model.*;
import com.sjsu.masters.mediauploadservice.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/video")
public class VideoFileController {


    private VideoFileService videoFileService;

    @Autowired
    public VideoFileController(VideoFileService videoFileService) {
        this.videoFileService = videoFileService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mono<List<VideoMetadata>> uploadMultipleFiles(@ModelAttribute VideoUploadRequest request) {
        log.info("Request Received : " + request.toString());
        return Mono.just(videoFileService.uploadMultipleFiles(request));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stream/api")
    public String getVideoStreamEndpoint(){
       return videoFileService.getVideoStreamingAPIEndpoint();
    }
}

package com.sjsu.masters.mediastreamservice.controller;

import com.sjsu.masters.mediastreamservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.http.server.reactive.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping(value = "/video")
public class VideoController {


    private VideoService videoService;


    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/stream/{accessCode}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                            @PathVariable("accessCode") String accessCode) {

        return Mono.just(videoService.prepareContent(accessCode, httpRangeList));

    }
}

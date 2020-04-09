package com.sjsu.masters.mediauploadservice.controller;

import com.sjsu.masters.mediauploadservice.model.*;
import com.sjsu.masters.mediauploadservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/video")
public class VideoFileController {


    private VideoFileService videoFileService;

    @Autowired
    public VideoFileController(VideoFileService videoFileService) {
        this.videoFileService = videoFileService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<VideoMetadata> uploadMultipleFiles(@ModelAttribute VideoUploadRequest request) {
        return videoFileService.uploadMultipleFiles(request);
    }
}

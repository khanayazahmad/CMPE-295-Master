package com.sjsu.masters.mediastreamservice.model;

import org.springframework.web.multipart.*;

import java.util.*;

public class VideoUploadRequest {

    private String ownerId;
    private String title;
    private List<MultipartFile> videoFiles;

    public VideoUploadRequest(String ownerId, String title, List<MultipartFile> videoFiles) {
        this.ownerId = ownerId;
        this.title = title;
        this.videoFiles = videoFiles;
    }

    public VideoUploadRequest() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MultipartFile> getVideoFiles() {
        return videoFiles;
    }

    public void setVideoFiles(List<MultipartFile> videoFiles) {
        this.videoFiles = videoFiles;
    }
}

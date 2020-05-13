package com.sjsu.masters.mediauploadservice.model;

import org.springframework.web.multipart.*;

import java.util.*;
import java.util.stream.*;

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

    @Override
    public String toString() {
        return "VideoUploadRequest{" +
                "ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", videoFiles=" + videoFiles
                .stream()
                .map(file->file.getName())
                .collect(Collectors.toList())
                .toString() +
                '}';
    }
}

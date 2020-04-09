package com.sjsu.masters.datamanagementservice.model;

import org.springframework.data.cassandra.core.cql.*;
import org.springframework.data.cassandra.core.mapping.*;


public class VideoMetadata {

    private String videoId;
    private String ownerId;
    private String title;
    private Long size;
    private String accessURL;
    private String fileName;
    private String bucketName;
    private Long createdAt;

    public VideoMetadata(String videoId, String ownerId, String title, Long size, String accessURL, String fileName, String bucketName, Long createdAt) {
        this.videoId = videoId;
        this.ownerId = ownerId;
        this.title = title;
        this.size = size;
        this.accessURL = accessURL;
        this.fileName = fileName;
        this.bucketName = bucketName;
        this.createdAt = createdAt;
    }

    public VideoMetadata() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getAccessURL() {
        return accessURL;
    }

    public void setAccessURL(String accessURL) {
        this.accessURL = accessURL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "VideoMetadata{" +
                "ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", accessURL='" + accessURL + '\'' +
                ", fileName='" + fileName + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

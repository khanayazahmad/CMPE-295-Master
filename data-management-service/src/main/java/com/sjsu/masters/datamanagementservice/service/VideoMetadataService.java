package com.sjsu.masters.datamanagementservice.service;

import com.sjsu.masters.datamanagementservice.model.*;
import com.sjsu.masters.datamanagementservice.repository.cassandra.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class VideoMetadataService {

    private VideoMetadataByAccessURLRepo videoMetadataByAccessURLRepo;
    private VideoMetadataByOwnerRepo videoMetadataByOwnerRepo;

    @Autowired
    public VideoMetadataService(VideoMetadataByAccessURLRepo videoMetadataByAccessURLRepo, VideoMetadataByOwnerRepo videoMetadataByOwnerRepo) {
        this.videoMetadataByAccessURLRepo = videoMetadataByAccessURLRepo;
        this.videoMetadataByOwnerRepo = videoMetadataByOwnerRepo;
    }

    public boolean save(VideoMetadata videoMetadata){
        videoMetadataByAccessURLRepo.save(new VideoMetadataByAccessURL(videoMetadata));
        videoMetadataByOwnerRepo.save(new VideoMetadataByOwner(videoMetadata));
        return true;
    }

    public boolean saveAll(List<VideoMetadata> videoMetadataList){
        Iterable<VideoMetadataByAccessURL> videoMetadataByAccessURLIterable = videoMetadataList
                .stream()
                .map(VideoMetadataByAccessURL::new)
                .collect(Collectors.toList());
        videoMetadataByAccessURLRepo.saveAll(videoMetadataByAccessURLIterable);

        Iterable<VideoMetadataByOwner> videoMetadataByOwnerIterable = videoMetadataList
                .stream()
                .map(VideoMetadataByOwner::new)
                .collect(Collectors.toList());
        videoMetadataByOwnerRepo.saveAll(videoMetadataByOwnerIterable);
        return true;
    }

    public VideoMetadata getVideoMetadataByAccessURL(String accessURL){
        return (VideoMetadata) videoMetadataByAccessURLRepo.findByAccessURL(accessURL);
    }

    public List<VideoMetadata> getVideoMetadataByOwner(String ownerId){
        return videoMetadataByOwnerRepo.findByOwnerId(ownerId)
                .stream()
                .map(videoMetadataByOwner -> (VideoMetadata)videoMetadataByOwner)
                .collect(Collectors.toList());
    }

}

package com.sjsu.masters.datamanagementservice.model;

import org.springframework.data.cassandra.core.cql.*;
import org.springframework.data.cassandra.core.mapping.*;

@Table(value = "video_metadata_by_owner")
public class VideoMetadataByOwner extends VideoMetadata {

    public VideoMetadataByOwner(
            String videoId,
            String ownerId,
            String title,
            Long size,
            String accessURL,
            String fileName,
            String bucketName,
            Long createdAt
    ) {
        super(videoId, ownerId, title, size, accessURL, fileName, bucketName, createdAt);
    }

    public VideoMetadataByOwner() {
    }

    public VideoMetadataByOwner(VideoMetadata videoMetadata) {
        super(
                videoMetadata.getVideoId(),
                videoMetadata.getOwnerId(),
                videoMetadata.getTitle(),
                videoMetadata.getSize(),
                videoMetadata.getAccessURL(),
                videoMetadata.getFileName(),
                videoMetadata.getBucketName(),
                videoMetadata.getCreatedAt()
        );
    }

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    public String getOwnerId() {
        return super.getOwnerId();
    }

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    public String getVideoId() {
        return super.getVideoId();
    }

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    public String getAccessURL() {
        return super.getAccessURL();
    }
}

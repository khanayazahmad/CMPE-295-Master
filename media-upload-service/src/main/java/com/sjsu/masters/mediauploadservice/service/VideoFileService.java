package com.sjsu.masters.mediauploadservice.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sjsu.masters.mediauploadservice.client.DMS.*;
import com.sjsu.masters.mediauploadservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class VideoFileService {

    @Value("${app.awsServices.bucketName}")
    private String S3_BUCKET_NAME;

    @Value("${media.stream.hostname}")
    private String MEDIA_STREAM_HOSTNAME;

    private String MEDIA_STREAM_ENDPOINT = "/video/stream/";

    private AmazonS3 amazonS3Client;

    private DataManagementService dataManagementService;

    @Autowired
    public VideoFileService(AmazonS3 amazonS3Client, DataManagementService dataManagementService) {
        this.amazonS3Client = amazonS3Client;
        this.dataManagementService = dataManagementService;
    }


    public String getVideoStreamingAPIEndpoint(){
        return MEDIA_STREAM_HOSTNAME + MEDIA_STREAM_ENDPOINT;
    }

    public List<VideoMetadata> uploadMultipleFiles(VideoUploadRequest request) {

        List<VideoMetadata> videoMetadataList = new ArrayList<>();

        if (request != null && !request.getVideoFiles().isEmpty()) {

            request.getVideoFiles().parallelStream().forEach(videoFile -> {

                File file = convertMultiPartFileToFile(videoFile);
                Long createdAt = System.currentTimeMillis();
                String uniqueFileName = createdAt+"_"
                        + request.getOwnerId() + "_"
                        + videoFile.getOriginalFilename();

                videoMetadataList.add(
                        getMetadata(
                                request.getOwnerId(),
                                request.getTitle(),
                                videoFile.getSize(),
                                uniqueFileName,
                                createdAt
                        )
                );

                uploadFileToS3bucket(uniqueFileName, file, S3_BUCKET_NAME);
                file.delete();
            });
            dataManagementService.createAll(RequestWrapper.wrap(videoMetadataList));
        }
        return videoMetadataList;
    }

    @Async
    void uploadFileToS3bucket(String fileName, File file, String bucketName) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

    }


    private VideoMetadata getMetadata(
            String ownerId,
            String title,
            Long size,
            String uniqueFileName,
            Long createdAt
    ){

        String videoId = UUID.randomUUID().toString() + createdAt + uniqueFileName.hashCode();
        String accessURL = videoId;
        return new VideoMetadata(
                videoId,
                ownerId,
                title,
                size,
                accessURL,
                uniqueFileName,
                S3_BUCKET_NAME,
                createdAt
        );
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}

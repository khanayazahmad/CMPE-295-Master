package com.sjsu.masters.mediauploadservice.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sjsu.masters.mediauploadservice.client.DMS.*;
import com.sjsu.masters.mediauploadservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
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

    private AmazonS3 amazonS3Client;

    private DataManagementService dataManagementService;

    @Autowired
    public VideoFileService(AmazonS3 amazonS3Client, DataManagementService dataManagementService) {
        this.amazonS3Client = amazonS3Client;
        this.dataManagementService = dataManagementService;
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

    private void uploadFileToS3bucket(String fileName, File file, String bucketName) {
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

    }

    private S3Object downloadFileFromS3bucket(String fileName, String bucketName) {
        S3Object object = amazonS3Client.getObject(bucketName,  fileName);
        return object;

    }

    private void deleteFileFromS3bucket(String fileName, String bucketName) {
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    private VideoMetadata getMetadata(
            String ownerId,
            String title,
            Long size,
            String uniqueFileName,
            Long createdAt
    ){
        String accessURL = uniqueFileName;
        String videoId = UUID.randomUUID().toString() + createdAt;
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

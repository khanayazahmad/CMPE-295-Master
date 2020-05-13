package com.sjsu.masters.mediauploadservice.service;
import com.amazonaws.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.sjsu.masters.mediauploadservice.client.DMS.*;
import com.sjsu.masters.mediauploadservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

@Slf4j
@Service
public class VideoFileService {

    @Value("${app.awsServices.bucketName}")
    private String S3_BUCKET_NAME;

    @Value("${media.stream.hostname}")
    private String MEDIA_STREAM_HOSTNAME;

    private String MEDIA_STREAM_ENDPOINT = "/video/stream/";

    private TransferManager transferManager;

    private DataManagementService dataManagementService;

    @Autowired
    public VideoFileService(TransferManager transferManager, DataManagementService dataManagementService) {
        this.dataManagementService = dataManagementService;
        this.transferManager = transferManager;
    }


    public String getVideoStreamingAPIEndpoint(){
        return MEDIA_STREAM_HOSTNAME + MEDIA_STREAM_ENDPOINT;
    }

    public List<VideoMetadata> uploadMultipleFiles(VideoUploadRequest request) {

        List<VideoMetadata> videoMetadataList = new ArrayList<>();

        if (request != null && !request.getVideoFiles().isEmpty()) {

            request.getVideoFiles().parallelStream().forEach(videoFile -> {


                try {
                    log.info(videoFile.getName()+ " >>>>>>>>>>>>>>>   " + "Creating InputStream ");
                    InputStream fileInputStream = videoFile.getInputStream();
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

                    uploadFileToS3bucket(uniqueFileName, fileInputStream, videoFile.getSize());

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });


            log.info("Calling data-management-service");
            ResponseEntity<String> response = dataManagementService.createAll(RequestWrapper.wrap(videoMetadataList));
            log.info("Received response data-management-service >>>>>>> " +response.toString());


        }

        log.info("Returning response for server " + videoMetadataList
                .stream()
                .map(vm -> vm.toString())
                .collect(Collectors.toList())
                .toString());
        return videoMetadataList;
    }


    void uploadFileToS3bucket(String fileName, InputStream in, Long contentLength) throws IOException, InterruptedException {

        try{
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(contentLength);
            PutObjectRequest request = new PutObjectRequest(S3_BUCKET_NAME, fileName, in, meta);
            AtomicReference<Long> transferredBytes = new AtomicReference<>(Long.valueOf(0));
            AtomicReference<Double> percentComplete = new AtomicReference<>(0.0);
            log.info("File: " + fileName + "   >>>>>>>>>>>>>>>   " + "Initiating Upload");
            Upload upload = transferManager.upload(request);
            upload.addProgressListener((com.amazonaws.event.ProgressListener) progressEvent -> {

                if(progressEvent.getBytesTransferred() > 0){
                    transferredBytes.updateAndGet(v -> v + progressEvent.getBytesTransferred());
                    double percent = Math.round((transferredBytes.get() * 100.0)/contentLength);
                    if(percent > 0 && percent%10 == 0 && percent>percentComplete.get()){
                        log.info("File: " + fileName + "   >>>>>>>>>>>>>>>   "+ transferredBytes.get() + " / " + contentLength
                                + " bytes transferred   |    percent : " + percent + " %");
                        percentComplete.set(percent);
                    }

                }

            });


        } catch (AmazonServiceException e) {

            e.printStackTrace();
        } catch (SdkClientException e) {

            e.printStackTrace();
        }

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

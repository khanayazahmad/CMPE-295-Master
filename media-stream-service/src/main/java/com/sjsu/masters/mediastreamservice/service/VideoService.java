package com.sjsu.masters.mediastreamservice.service;

import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.*;
import com.sjsu.masters.mediastreamservice.client.DMS.*;
import com.sjsu.masters.mediastreamservice.model.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.io.*;

@Slf4j
@Service
public class VideoService {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String VIDEO_CONTENT = "video/";
    private static final String CONTENT_RANGE = "Content-Range";
    private static final String ACCEPT_RANGES = "Accept-Ranges";
    private static final String BYTES = "bytes";

    @Value("${size.chunk.mb}")
    private int CHUNK_MB;

    private int BYTE_RANGE = 1024;

    private AmazonS3 amazonS3Client;

    private DataManagementService dataManagementService;

    @Autowired
    public VideoService(AmazonS3 amazonS3Client, DataManagementService dataManagementService) {
        this.amazonS3Client = amazonS3Client;
        this.dataManagementService = dataManagementService;
    }

    public byte[] readByteRange(VideoMetadata videoMetadata, long start, long end) throws IOException {

        GetObjectRequest rangeObjectRequest = new GetObjectRequest(videoMetadata.getBucketName(), videoMetadata.getFileName());
        rangeObjectRequest.setRange(start, end);
        S3Object s3Object = amazonS3Client.getObject(rangeObjectRequest);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] result = IOUtils.toByteArray(inputStream);
        return result;

    }



    public ResponseEntity<byte[]> prepareContent(String accessCode, String range) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        long CHUNK_SIZE = BYTE_RANGE * 1024 * (CHUNK_MB);


        VideoMetadata videoMetadata = dataManagementService.getByAccessURL(accessCode).getBody();
        String fullFileName = videoMetadata.getFileName();
        String fileType= fullFileName.split("\\.")[1];
        try {
            fileSize = videoMetadata.getSize();
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                        .header(CONTENT_LENGTH, String.valueOf(fileSize - 1))
                        .body(readByteRange(videoMetadata, rangeStart, fileSize)); // Read the object and convert it as bytes
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = Math.min(fileSize, rangeStart + CHUNK_SIZE);
            }
            data = readByteRange(videoMetadata, rangeStart, rangeEnd);
        } catch (IOException e) {
            log.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, contentLength)
                .header(CONTENT_RANGE, BYTES + " " + rangeStart + "-" + (rangeEnd - 1) + "/" + fileSize)
                .body(data);


    }



}

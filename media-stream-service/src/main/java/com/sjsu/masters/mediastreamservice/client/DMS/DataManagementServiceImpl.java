package com.sjsu.masters.mediastreamservice.client.DMS;

import com.google.gson.*;
import com.sjsu.masters.mediastreamservice.model.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class DataManagementServiceImpl implements DataManagementService {
    @Override
    public ResponseEntity<String> createAll(RequestWrapper<List<VideoMetadata>> request) {
        Gson gson = new Gson();
        log.error("DataManagementService Failure: Fallback successful");
        log.debug(gson.toJson(request));
        return ResponseEntity.ok("FALLBACK");
    }

    @Override
    public ResponseEntity<VideoMetadata> getByAccessURL(String accessUrl) {

        log.error("DataManagementService Failure: Fallback successful");
        log.debug("Access URL: "+ accessUrl);
        return null;
    }
}

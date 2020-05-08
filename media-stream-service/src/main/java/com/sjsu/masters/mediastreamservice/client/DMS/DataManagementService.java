package com.sjsu.masters.mediastreamservice.client.DMS;

import com.sjsu.masters.mediastreamservice.model.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@FeignClient(value="data-management-service", url="${client.dms.ip}", fallback = DataManagementServiceImpl.class)
public interface DataManagementService {

    @RequestMapping(value="/video-metadata", method= RequestMethod.POST, consumes = "application/json")
    @ResponseBody ResponseEntity<String> createAll(@RequestBody RequestWrapper<List<VideoMetadata>> request);

    @RequestMapping(method= RequestMethod.GET, value = "/video-metadata/{accessUrl}", produces = "application/json")
    @ResponseBody ResponseEntity<VideoMetadata> getByAccessURL(@PathVariable(value = "accessUrl") String accessUrl);

}

package com.sjsu.masters.mediauploadservice.client.DMS;

import com.sjsu.masters.mediauploadservice.model.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@FeignClient(value="data-management-service", url="${client.dms.ip}", fallback = DataManagementServiceImpl.class)
public interface DataManagementService {

    @RequestMapping(value="/video-metadata", method= RequestMethod.POST, consumes = "application/json")
    @ResponseBody ResponseEntity<String> createAll(@RequestBody RequestWrapper<List<VideoMetadata>> request);
}

package com.sjsu.masters.datamanagementservice.repository.cassandra;

import com.sjsu.masters.datamanagementservice.model.*;
import org.springframework.data.cassandra.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface VideoMetadataByOwnerRepo extends CassandraRepository<VideoMetadataByOwner, String> {
    List<VideoMetadataByOwner> findByOwnerId(String ownerId);
}

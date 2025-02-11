package com.zyn.storage.file.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.ShareService;

@FeignClient(value = "share-service")
public interface ShareRemote extends ShareService {
}

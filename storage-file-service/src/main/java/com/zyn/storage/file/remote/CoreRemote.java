package com.zyn.storage.file.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.CoreService;

@FeignClient(value = "core-service")
public interface CoreRemote extends CoreService {
}

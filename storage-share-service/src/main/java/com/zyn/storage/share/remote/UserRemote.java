package com.zyn.storage.share.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.UserService;

@FeignClient(value = "user-service")
public interface UserRemote extends UserService {
}

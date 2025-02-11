package com.zyn.storage.user.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.EdgeService;

/**
 * 边缘服务API
 *
 * @author: zhouyongnan
 * @date: 2024/9/26
 */
@FeignClient(value = "edge-service")
public interface EdgeRemote extends EdgeService {
}

package com.zyn.storage.user.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.CoreService;

/**
 * 核心服务API
 *
 * @author: zhouyongnan
 * @date: 2024/9/26
 */
@FeignClient(value = "core-service")
public interface CoreRemote extends CoreService {
}

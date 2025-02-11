package com.zyn.storage.user.remote;

import org.springframework.cloud.openfeign.FeignClient;
import com.zyn.storage.serviceapi.service.FileService;

/**
 * 文件调用API
 *
 * @author: zhouyongnan
 * @date: 2024/9/26
 */
@FeignClient(value = "file-service")
public interface FileRemote extends FileService {
}

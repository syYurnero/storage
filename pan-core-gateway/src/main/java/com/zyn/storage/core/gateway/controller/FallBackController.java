package com.zyn.storage.core.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zyn.storage.enums.ResultCodeEnum;
import com.zyn.storage.request.base.RestAPIResultDTO;

/**
 * 服务调用失败返回
 * 
 * @author: zhouyongnan
 * @date: 2024/11/21
 */
@RestController
@RequestMapping("/fallback")
public class FallBackController {
    @RequestMapping("")
    public RestAPIResultDTO<String> fallback() {
        return RestAPIResultDTO.Error(null, ResultCodeEnum.UNAVAILABLE);
    }
}

package com.zyn.storage.edge.service;

import com.zyn.storage.request.SendSmsRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

public interface ISendSmsService {
    /**
     * 发送短信数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> sendSmsHandle(SendSmsRequest request);

}

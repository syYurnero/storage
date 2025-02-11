package com.zyn.storage.file.service;

import com.zyn.storage.request.base.RestAPIResultDTO;

public interface IMd5Service {
    /**
     * MD5校验数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/11/1
     */
    RestAPIResultDTO<String> md5CheckHandle(String fid, String md5);
}

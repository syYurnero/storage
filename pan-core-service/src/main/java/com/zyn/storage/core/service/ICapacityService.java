package com.zyn.storage.core.service;

import com.zyn.storage.request.base.RestAPIResultDTO;

/**
 * 容量接口
 *
 * @author: zhouyongnan
 * @date: 2024/10/29
 */
public interface ICapacityService {
    /**
     * 容量查询数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    RestAPIResultDTO<String> useCapacityHandle(String uid);

    /**
     * 初始化容量数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<Integer> initCapacityHandle(String uid);
}

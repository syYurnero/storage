package com.zyn.storage.user.service;

import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.response.UserInfoDTO;

public interface PassportService {


    /**
     * 用户登录数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/8/9
     */
    RestAPIResultDTO<String> loginHandle(String username, String password, String publicKey) throws Exception;

    /**
     * 用户推出数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/8/9
     */
    RestAPIResultDTO<String> logoutHandle(String token);

    /**
     * 获取用户信息数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/8/9
     */
    RestAPIResultDTO<UserInfoDTO> getUserInfoHandle(String userId);
}

package com.zyn.storage.user.service;

import com.zyn.storage.request.ForgetPhoneSendRequest;
import com.zyn.storage.request.ModifyPassRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

public interface PasswordService {
    /**
     * 忘记密码短信服务数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> forgetPhoneSendHandle(ForgetPhoneSendRequest request);

    /**
     * 手机号/用户名校验数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> checkPhoneSendHandle(String username);

    /**
     * 忘记密码的修改数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> modifyPassHandle(ModifyPassRequest request);
}

package com.zyn.storage.user.service;

import org.springframework.web.multipart.MultipartFile;
import com.zyn.storage.request.ChangePwdRequest;
import com.zyn.storage.request.RegPhoneSendRequest;
import com.zyn.storage.request.UserRegistRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

import java.io.IOException;

public interface RegisterService {
    /**
     * 用户名查重数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<String> checkUserNameHandle(String username);

    /**
     * 手机号查重数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<String> checkPhoneHandle(String phoneNum);

    /**
     * 用户注册数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<String> userRegistHandle(UserRegistRequest request) throws Exception;

    /**
     * 注册发送短信数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> regPhoneSendHandle(RegPhoneSendRequest request);

    /**
     * 修改密码数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> changePwdHandle(ChangePwdRequest request);

    /**
     * 加载用户头像数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> loadImgHandle(String uid);

    /**
     * 上传用户头像数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> uploadPicHandle(String uid, MultipartFile file) throws IOException;
}

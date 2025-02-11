package com.zyn.storage.edge.service;

import com.zyn.storage.request.base.RestAPIResultDTO;

import javax.servlet.http.HttpServletResponse;

public interface IEdgeService {
    /**
     * 检查密码格式数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> regCheckPwdHandle(String password, String publicKey);

    /**
     * 生成公钥数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/10/31
     */
    RestAPIResultDTO<String> getPublicKeyHandle() throws Exception;

    /**
     * 生成验证码数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    void getVerfyImgHandle(String vcodestr, HttpServletResponse response);
}

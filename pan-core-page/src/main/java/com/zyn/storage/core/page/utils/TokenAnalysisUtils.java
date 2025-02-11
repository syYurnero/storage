package com.zyn.storage.core.page.utils;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import com.zyn.storage.response.UserInfoDTO;
import com.zyn.storage.utils.JSONUtils;
import com.zyn.storage.utils.JWTUtils;

/**
 * Token分析工具类
 *
 * @author: zhouyongnan
 * @date: 2024/9/27
 */
@Component
public class TokenAnalysisUtils {
    public UserInfoDTO tokenAnalysis(String token) {
        Claims claims = JWTUtils.parseJWT(token, "nimadetou".getBytes());
        String subject = claims.getSubject();
        UserInfoDTO userInfoDTO = JSONUtils.parseObject(subject, UserInfoDTO.class);
        return userInfoDTO;
    }
}

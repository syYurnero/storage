package com.zyn.storage.user.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.zyn.storage.constant.RedisConstants;
import com.zyn.storage.framework.redis.core.utils.RedisUtil;
import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.response.UserInfoDTO;
import com.zyn.storage.user.dao.UserInfoDao;
import com.zyn.storage.user.entity.UserInfoDO;
import com.zyn.storage.user.service.PassportService;
import com.zyn.storage.utils.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public RestAPIResultDTO<String> loginHandle(String username, String password, String publicKey) throws Exception {
        String rsaKey = redisUtil.get(publicKey);
        try {
            password = RSAUtils.decryptDataOnJava(password, rsaKey);
        } catch (Exception e) {
            return RestAPIResultDTO.Error("密码错误");
        }
        UserInfoDO userInfoDO = userInfoDao.getUserInfoByPassport(username);
        if (userInfoDO != null) {
            if (MD5Utils.verify(password, userInfoDO.getPassword())) {
                String accessToken = JWTUtils.createJWT(IDUtils.showNextId(new Random().nextInt(30)).toString(),
                        JSONUtils.toJSONString(userInfoDO), 30 * 24 * 60 * 60 * 1000);

                CookieUtils.addCookie("token", accessToken);
                CookieUtils.addCookie("uid", userInfoDO.getUserId());
                redisUtil.delete(publicKey);
                return RestAPIResultDTO.Success("登录成功");
            } else {
                return RestAPIResultDTO.Error("密码错误");
            }
        }
        return RestAPIResultDTO.Error("用户信息不存在");
    }

    @Override
    public RestAPIResultDTO<String> logoutHandle(String token) {
        if (!StringUtils.isEmpty(token)) {
            JWTUtils.parseJWT(token, "nimadetou".getBytes());
            CookieUtils.removeCookie("token");
            CookieUtils.removeCookie("uid");
            redisUtil.setEx(String.format(RedisConstants.LOGOUT, token), token, 60 * 60 * 24 * 365, TimeUnit.SECONDS);
            return RestAPIResultDTO.Success("退出成功");
        } else {
            return RestAPIResultDTO.Error("token不能为空");
        }
    }

    @Override
    public RestAPIResultDTO<UserInfoDTO> getUserInfoHandle(String userId) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfoDO userInfoDO = userInfoDao.getUserInfoByUserId(userId);
        if (userInfoDO != null) {
            BeanUtils.copyProperties(userInfoDO, userInfoDTO);
        }
        return RestAPIResultDTO.Success(userInfoDTO);
    }
}

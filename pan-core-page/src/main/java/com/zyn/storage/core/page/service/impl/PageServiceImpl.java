package com.zyn.storage.core.page.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import com.zyn.storage.constant.RedisConstants;
import com.zyn.storage.core.page.service.IPageService;
import com.zyn.storage.core.page.utils.TokenAnalysisUtils;
import com.zyn.storage.framework.redis.core.utils.RedisUtil;
import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.response.ShareDTO;
import com.zyn.storage.response.UserInfoDTO;
import com.zyn.storage.utils.CookieUtils;
import com.zyn.storage.utils.HttpClientUtils;
import com.zyn.storage.utils.JSONUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 页面跳转数据处理类
 *
 * @author: zhouyongnan
 * @date: 2024/9/27
 */
@Service
public class PageServiceImpl implements IPageService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenAnalysisUtils tokenAnalysisUtils;

    /**
     * 首页跳转数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    @Override
    public String indexHandle() {
        String token = CookieUtils.getCookie("token");
        if (StringUtils.isEmpty(token)) {
            return "login";
        } else {
            return "redirect:/disk/home";
        }
    }

    /**
     * 跳转到主页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    @Override
    public String homeHandle(Model model) {
        String token = CookieUtils.getCookie("token");
        if (!StringUtils.isEmpty(token)) {
            try {
                if (redisUtil.hasKey(String.format(RedisConstants.LOGOUT, token))) {
                    return "login";
                } else {
                    UserInfoDTO userInfoDTO = tokenAnalysisUtils.tokenAnalysis(token);
                    model.addAttribute("name", userInfoDTO.getUserName());
                    return "index";
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException exception) {
                exception.printStackTrace();
                return "login";
            }
        }
        return "login";
    }

    /**
     * 跳转到分享管理页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    @Override
    public String shareHandle(Model model) {
        String token = CookieUtils.getCookie("token");
        if (!StringUtils.isEmpty(token)) {
            try {
                if (redisUtil.hasKey(String.format(RedisConstants.LOGOUT, token))) {
                    return "login";
                } else {
                    UserInfoDTO userInfoDTO = tokenAnalysisUtils.tokenAnalysis(token);
                    model.addAttribute("name", userInfoDTO.getUserName());
                    return "share";
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException exception) {
                exception.printStackTrace();
                return "login";
            }
        }
        return "login";
    }

    /**
     * 查看分享页面数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/27
     */
    @Override
    public String sHandle(Model model, String shareId) {
        String token = CookieUtils.getCookie("token");
        if (!StringUtils.isEmpty(token)) {
            try {
                if (redisUtil.hasKey(String.format(RedisConstants.LOGOUT, token))) {
                    return "login";
                } else {
                    String coreGatewayUrl = "";
                    UserInfoDTO userInfoDTO = tokenAnalysisUtils.tokenAnalysis(token);
                    ClassPathResource classPathResource = new ClassPathResource("static/assets2/js/config.js");
                    File file = classPathResource.getFile();
                    BufferedReader reader = null;
                    try {
                        System.out.println("以行为单位读取文件内容，一次读一行");
                        reader = new BufferedReader(new FileReader(file));
                        String tempString;
                        while ((tempString = reader.readLine()) != null) {
                            if(tempString.contains("CORE_GATEWAY_URL")){
                                coreGatewayUrl = tempString.substring(tempString.indexOf("= \"") + 3,tempString.length()-1);
                            }
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    String getShareUserUrl = coreGatewayUrl + "/api/share/getshareuser?shareId=" + shareId;
                    String resultString = HttpClientUtils.HttpGet(getShareUserUrl);
                    RestAPIResultDTO restAPIResultDTO = JSONUtils.parseObject(resultString, RestAPIResultDTO.class);
                    Map<String, Object> map = (Map<String, Object>) restAPIResultDTO.getRespData();
                    UserInfoDTO shareUserInfoDTO = JSONUtils.parseObject((String) map.get("userinfo"), UserInfoDTO.class);
                    if (shareUserInfoDTO == null) {
                        model.addAttribute("name", userInfoDTO.getUserName());
                        return "sError";
                    } else if (shareUserInfoDTO.getUserId().equals(userInfoDTO.getUserId())) {
                        model.addAttribute("name", userInfoDTO.getUserName());
                        return "share";
                    }
                    ShareDTO shareDTO = JSONUtils.parseObject((String) map.get("share"), ShareDTO.class);
                    if (shareDTO.getExpiration() != null && shareDTO.getExpiration().getTime() - (new Date().getTime()) < 0) {
                        return "sError";
                    }
                    model.addAttribute("name", userInfoDTO.getUserName());
                    model.addAttribute("shareUser", shareUserInfoDTO.getUserName());
                    if (shareDTO.getMultiWhether() == 1) {
                        model.addAttribute("shareName", shareDTO.getTheme() + "等文件");
                    } else {
                        model.addAttribute("shareName", shareDTO.getTheme() + "文件");
                    }
                    model.addAttribute("shareId", shareDTO.getShareId());
                    return "s";
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | IOException exception) {
                exception.printStackTrace();
                return "login";
            }
        }
        return "login";
    }
}

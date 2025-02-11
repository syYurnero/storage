package com.zyn.storage.serviceapi.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.zyn.storage.response.UserInfoDTO;
import com.zyn.storage.request.base.RestAPIResultDTO;

public interface UserService {
    @RequestMapping(value = "getuserinfo", method = RequestMethod.POST)
    RestAPIResultDTO<UserInfoDTO> getUserInfo(@RequestParam("userId") String userId);
}

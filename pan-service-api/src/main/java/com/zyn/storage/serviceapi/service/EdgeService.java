package com.zyn.storage.serviceapi.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zyn.storage.request.SendSmsRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

public interface EdgeService {
    @RequestMapping(value = "sendSms", method = RequestMethod.POST)
    RestAPIResultDTO<String> sendSms(@RequestBody SendSmsRequest request);
}

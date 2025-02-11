package com.zyn.storage.edge.service.impl;

import org.springframework.stereotype.Service;
import com.zyn.storage.edge.service.ISendSmsService;
import com.zyn.storage.edge.utils.AbsRestClient;
import com.zyn.storage.edge.utils.JsonReqClient;
import com.zyn.storage.pojo.SmsResult;
import com.zyn.storage.request.SendSmsRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.utils.JSONUtils;

@Service
public class SendSmsServiceImpl implements ISendSmsService {
    @Override
    public RestAPIResultDTO<String> sendSmsHandle(SendSmsRequest request) {
        String jsonResult = InstantiationRestAPI().sendSms(request.getSid(), request.getToken(), request.getAppid(), request.getTemplateid(), request.getParam(), request.getMobile(), request.getUid());
        System.out.println("Response content is: " + jsonResult);
        SmsResult result = JSONUtils.parseObject(jsonResult, SmsResult.class);
        if (!result.getMsg().equals("OK")) {
            return RestAPIResultDTO.Error("发送失败");
        }
        return RestAPIResultDTO.Success("发送成功");
    }

    static AbsRestClient InstantiationRestAPI() {
        return new JsonReqClient();
    }
}

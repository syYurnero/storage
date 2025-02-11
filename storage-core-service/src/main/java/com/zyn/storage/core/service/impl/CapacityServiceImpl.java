package com.zyn.storage.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zyn.storage.core.dao.CapacityDao;
import com.zyn.storage.core.entity.CapacityDO;
import com.zyn.storage.core.service.ICapacityService;
import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.utils.JSONUtils;

@Service
public class CapacityServiceImpl implements ICapacityService {
    @Autowired
    private CapacityDao capacityDao;

    @Override
    public RestAPIResultDTO<String> useCapacityHandle(String uid) {
        CapacityDO capacityDO = capacityDao.getCapacity(uid);
        String useJson = JSONUtils.toJSONString(capacityDO);
        return RestAPIResultDTO.Success(useJson, "成功");
    }

    @Override
    public RestAPIResultDTO<Integer> initCapacityHandle(String uid) {
        RestAPIResultDTO<Integer> panResult = new RestAPIResultDTO<>();
        CapacityDO capacityDO = new CapacityDO();
        capacityDO.setUserId(uid);
        capacityDO.setTotalCapacity(5368709120L);
        capacityDO.setUsedCapacity(0L);
        Integer result = capacityDao.saveCapacity(capacityDO);
        return RestAPIResultDTO.Success(result, "成功");
    }
}

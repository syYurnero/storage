package com.zyn.storage.share.service;

import com.zyn.storage.request.AddShareViewCountRequest;
import com.zyn.storage.request.SaveShareRequest;
import com.zyn.storage.request.ShareListRequest;
import com.zyn.storage.request.ShareRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

import java.util.Map;

public interface IShareService {
    /**
     * 分享文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/11/1
     */
    RestAPIResultDTO<String> shareHandle(ShareRequest request);

    /**
     * 分享列表数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<Map<String, Object>> shareListHandle(ShareListRequest request);

    /**
     * 取消分享数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> unShareHandle(String uid, String shareIds);

    /**
     * 获取分享用户信息数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<Map<String, Object>> getShareUserHandle(String shareId);

    /**
     * 保存分享数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> saveShareHandle(SaveShareRequest request);

    /**
     * 查询分享是否带密码数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> checkLockHandle(String shareId);

    /**
     * 验证分享密码数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> verifykLockHandle(String lockPassword, String shareId);

    /**
     * 根据分享ID获取虚拟地址ID数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<Map<String, Object>> getVinfoHandle(String shareId, String lockPassword);

    /**
     * 增加分享访问量数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    void addShareViewCountHandle(AddShareViewCountRequest request);

    /**
     * 增加分享下载量数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    void addShareDownloadCountHandle(String shareId);
}

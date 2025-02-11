package com.zyn.storage.request;

/**
 * 增加分享请求实体
 *
 * @author: zhouyongnan
 * @date: 2024/9/27
 */
public class AddShareViewCountRequest {
    private String shareId;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    @Override
    public String toString() {
        return "AddShareViewCountRequest{" +
                "shareId='" + shareId + '\'' +
                '}';
    }
}

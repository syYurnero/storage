package com.zyn.storage.file.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IDownloadService {
    /**
     * 下载文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    void downloadHandle(String uid, String vids, HttpServletResponse res) throws IOException;

    /**
     * 下载分享文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    void downloadShareHandle(String lockPassword, String shareId, HttpServletResponse res) throws IOException;
}

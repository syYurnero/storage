package com.zyn.storage.core.service;

import com.zyn.storage.request.CopyOrMoveFileRequest;
import com.zyn.storage.request.CreateDirRequest;
import com.zyn.storage.request.CreateVirtualAddressRequest;
import com.zyn.storage.request.RenameFileOrDirRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

import java.util.Map;

public interface IUpdateContentService {
    /**
     * 重命名文件或文件夹数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    RestAPIResultDTO<String> renameFileOrDirHandle(RenameFileOrDirRequest request);

    /**
     * 删除文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    RestAPIResultDTO<String> deleteFileHandle(String vids);


    /**
     * 创建文件夹数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/24
     */
    RestAPIResultDTO<Map<String, Object>> createDirHandle(CreateDirRequest request);

    /**
     * 复制或移动文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/10/29
     */
    RestAPIResultDTO<String> copyOrMoveFileHandle(CopyOrMoveFileRequest request);

    /**
     * 创建虚拟地址数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/10/29
     */
    RestAPIResultDTO<Integer> createVirtualAddressHandle(CreateVirtualAddressRequest request);
}

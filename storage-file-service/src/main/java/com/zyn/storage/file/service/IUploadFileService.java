package com.zyn.storage.file.service;

import org.springframework.web.multipart.MultipartFile;
import com.zyn.storage.request.QuickUploadFileRequest;
import com.zyn.storage.request.UploadFileRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IUploadFileService {
    /**
     * 上传文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<String> uploadFileHandle(UploadFileRequest request) throws IOException;

    /**
     * 秒传文件数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    RestAPIResultDTO<String> quickUploadFileHandle(QuickUploadFileRequest request) throws UnsupportedEncodingException;

    /**
     * 上传文件(内部调用)数据处理
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    RestAPIResultDTO<String> uploadHandle(MultipartFile file) throws IOException;
}

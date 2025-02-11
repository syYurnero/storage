package com.zyn.storage.core.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zyn.storage.core.dao.VirtualAddressDao;
import com.zyn.storage.core.entity.VirtualAddressDO;
import com.zyn.storage.core.service.IQueryContentService;
import com.zyn.storage.pojo.FolderInfo;
import com.zyn.storage.request.CheckDirWhetherRequest;
import com.zyn.storage.request.ListFileRequest;
import com.zyn.storage.request.ListFolderRequest;
import com.zyn.storage.request.SearchFileRequest;
import com.zyn.storage.request.base.RestAPIResultDTO;
import com.zyn.storage.response.VirtualAddressDTO;
import com.zyn.storage.utils.JSONUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryContentServiceImpl implements IQueryContentService {
    @Autowired
    private VirtualAddressDao virtualAddressDao;

    @Override
    public RestAPIResultDTO<Map<String, Object>> listFileHandle(ListFileRequest request) throws UnsupportedEncodingException {
        String path = request.getPath();
        if (request.getPath() != null) {
            path = URLDecoder.decode(path, "UTF-8");
        }
        PageHelper.startPage(request.getPage(), 100);
        if (request.getDesc().equals(1)) {
            if (request.getType().equals("all")) {
                PageHelper.orderBy("dir_whether desc," + request.getOrder() + " desc");
            } else {
                PageHelper.orderBy(request.getOrder() + " desc");
            }
        } else {
            if (request.getType().equals("all")) {
                PageHelper.orderBy("dir_whether desc," + request.getOrder() + " asc");
            } else {
                PageHelper.orderBy(request.getOrder() + " asc");
            }
        }
        Integer type = 0;
        String parentPath = null;
        if (request.getType().equals("all")) {
            parentPath = path;
            type = null;
        } else if (request.getType().equals("pic")) {
            type = 1;
        } else if (request.getType().equals("doc")) {
            type = 2;
        } else if (request.getType().equals("video")) {
            type = 3;
        } else if (request.getType().equals("mbt")) {
            type = 4;
        } else if (request.getType().equals("music")) {
            type = 5;
        } else if (request.getType().equals("other")) {
            type = 6;
        } else {
            type = 7;
        }
        List<VirtualAddressDO> virtualAddressDOList = virtualAddressDao.listVirtualAddress(request.getUid(), parentPath, type);
        if (virtualAddressDOList != null && virtualAddressDOList.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            for (VirtualAddressDO virtualAddressDO : virtualAddressDOList) {
                map.put(i++ + "", JSONUtils.toJSONString(virtualAddressDO));
            }
            return RestAPIResultDTO.Success(map);
        } else {
            return RestAPIResultDTO.Success(null);
        }
    }

    @Override
    public RestAPIResultDTO<Map<String, Object>> listFolderHandle(ListFolderRequest request) throws UnsupportedEncodingException {
        String parentPath = request.getParentPath();
        if (parentPath != null) {
            parentPath = URLDecoder.decode(parentPath, "UTF-8");
        }

        List<FolderInfo> folderInfos = new ArrayList<>();
        PageHelper.orderBy("file_name desc");
        List<VirtualAddressDO> virtualaddressDOList = virtualAddressDao.listVirtualAddress(request.getUid(), parentPath, 0);
        if (virtualaddressDOList != null && virtualaddressDOList.size() > 0) {
            for (VirtualAddressDO virtualAddressDO : virtualaddressDOList) {
                List<VirtualAddressDO> inVirtualAddressDOList = virtualAddressDao.listVirtualAddress(request.getUid(), parentPath.equals("/") ? parentPath + virtualAddressDO.getFileName() : parentPath + "/" + virtualAddressDO.getFileName(), 0);
                FolderInfo folderInfo = new FolderInfo();
                if (parentPath.equals("/")) {
                    folderInfo.setPath(parentPath + virtualAddressDO.getFileName());
                } else {
                    folderInfo.setPath(parentPath + "/" + virtualAddressDO.getFileName());
                }
                if (inVirtualAddressDOList != null && inVirtualAddressDOList.size() > 0) {
                    folderInfo.setDir_empty(1);
                } else {
                    folderInfo.setDir_empty(0);
                }
                folderInfos.add(folderInfo);
            }
            if (folderInfos.size() > 0) {
                Map<String, Object> map = new HashMap<>();
                int i = 0;
                for (FolderInfo folderInfo : folderInfos) {
                    map.put(i++ + "", JSONUtils.toJSONString(folderInfo));
                }
                return RestAPIResultDTO.Success(map);
            } else {
                return RestAPIResultDTO.Success(null);
            }
        } else {
            return null;
        }
    }

    @Override
    public RestAPIResultDTO<Map<String, Object>> searchFileHandle(SearchFileRequest request) {
        PageHelper.startPage(request.getPage(), 100);
        PageHelper.orderBy("dir_whether desc," + request.getOrder() + " desc");
        List<VirtualAddressDO> virtualAddressDOList = virtualAddressDao.listVirtualAddressLikeFileName(request.getUid(), request.getKey());
        if (virtualAddressDOList != null && virtualAddressDOList.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            for (VirtualAddressDO virtualAddressDO : virtualAddressDOList) {
                map.put(i++ + "", JSONUtils.toJSONString(virtualAddressDO));
            }
            return RestAPIResultDTO.Success(map);
        } else {
            return RestAPIResultDTO.Success(null);
        }
    }

    @Override
    public RestAPIResultDTO<Integer> checkDirWhetherHandle(CheckDirWhetherRequest request) {
        Integer count = virtualAddressDao.checkVirtualAddress(request.getUid(), request.getParentPath(), null, request.getDirName(), null);
        return RestAPIResultDTO.Success(count, "成功");
    }

    @Override
    public RestAPIResultDTO<String> getFileNameByVidHandle(String vid, String uid) {
        VirtualAddressDO virtualAddressDO = virtualAddressDao.getVirtualAddress(vid);
        return RestAPIResultDTO.Success(virtualAddressDO.getFileName(), "成功");
    }

    @Override
    public RestAPIResultDTO<VirtualAddressDTO> getVirtualaddressHandle(String vid, String uid) {
        VirtualAddressDTO virtualaddressDTO = new VirtualAddressDTO();
        VirtualAddressDO virtualAddressDO = virtualAddressDao.getVirtualAddress(vid);
        BeanUtils.copyProperties(virtualAddressDO, virtualaddressDTO);
        return RestAPIResultDTO.Success(virtualaddressDTO);
    }
}

package top.quhailong.pan.share.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.quhailong.pan.enums.ResultCodeEnum;
import top.quhailong.pan.request.*;
import top.quhailong.pan.request.base.RestAPIResultDTO;
import top.quhailong.pan.response.ShareDTO;
import top.quhailong.pan.response.UserInfoDTO;
import top.quhailong.pan.response.VirtualAddressDTO;
import top.quhailong.pan.share.dao.ShareDao;
import top.quhailong.pan.share.dao.ShareMapDao;
import top.quhailong.pan.share.entity.ShareDO;
import top.quhailong.pan.share.entity.ShareMapDO;
import top.quhailong.pan.share.remote.CoreRemote;
import top.quhailong.pan.share.remote.UserRemote;
import top.quhailong.pan.share.service.IShareService;
import top.quhailong.pan.utils.BeanUtils;
import top.quhailong.pan.utils.IDUtils;
import top.quhailong.pan.utils.JSONUtils;

import java.util.*;

@Service
public class ShareServiceImpl implements IShareService {
    @Autowired
    private CoreRemote coreRemote;
    @Autowired
    private UserRemote userRemote;
    @Autowired
    private ShareDao shareDao;
    @Autowired
    private ShareMapDao shareMapDao;

    @Override
    public RestAPIResultDTO<String> shareHandle(ShareRequest request) {
        List<String> vidList = JSONUtils.parseObject(request.getVids(), List.class);
        if (vidList == null) {
            return null;
        }
        String fileName = coreRemote.getFileNameByVid(vidList.get(0), request.getUid()).getRespData();
        String shareId = IDUtils.showNextId(new Random().nextInt(30)).toString();
        ShareDO shareDO = new ShareDO();
        shareDO.setCreateTime(new Date());
        shareDO.setDownloadCount(0);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if (request.getExpiration().equals("forever")) {
            c.add(Calendar.YEAR, 100);
            Date newDate = c.getTime();
            shareDO.setExpiration(newDate);
        } else if (request.getExpiration().equals("seven")) {
            c.add(Calendar.DATE, 7);
            Date newDate = c.getTime();
            shareDO.setExpiration(newDate);
        } else if (request.getExpiration().equals("one")) {
            c.add(Calendar.DATE, 1);
            Date newDate = c.getTime();
            shareDO.setExpiration(newDate);
        }
        if (request.getFlag().equals("public")) {
            shareDO.setSharePassword("");
            shareDO.setLockWhether(0);
        } else if (request.getFlag().equals("private")) {
            String words = "ABCDEFGHIJKMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
            StringBuffer sb = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                // 生成一个随机数字
                int index = random.nextInt(words.length()); // 生成随机数 0 到 length - 1
                // 获得字母数字
                char c1 = words.charAt(index);
                sb.append(c1);
            }
            shareDO.setSharePassword(sb.toString());
            shareDO.setLockWhether(1);
        }
        if (vidList.size() > 1) {
            shareDO.setMultiWhether(1);
        } else {
            shareDO.setMultiWhether(0);
        }
        shareDO.setSaveCount(0);
        shareDO.setShareId(shareId);
        shareDO.setTheme(fileName);
        shareDO.setUserId(request.getUid());
        shareDO.setVisitCount(0);
        shareDao.saveShare(shareDO);
        for (String vid : vidList) {
            ShareMapDO shareMapDO = new ShareMapDO();
            shareMapDO.setVirtualAddressId(vid);
            shareMapDO.setShareId(shareId);
            shareMapDao.saveShareMap(shareMapDO);
        }
        if (request.getFlag().equals("public")) {
            return RestAPIResultDTO.Success(shareId, "成功");
        } else if (request.getFlag().equals("private")) {
            return RestAPIResultDTO.Success(shareId + "," + shareDO.getSharePassword(), "成功");
        }
        return RestAPIResultDTO.Error(null, ResultCodeEnum.PARAMATER_ERROR);
    }

    /**
     * 分享列表数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<Map<String, Object>> shareListHandle(ShareListRequest request) {
        PageHelper.startPage(request.getPage(), 100);
        if (request.getDesc().equals(1)) {
            PageHelper.orderBy(request.getOrder() + " desc");
        } else {
            PageHelper.orderBy(request.getOrder() + " asc");
        }
        List<ShareDO> shareDOList = shareDao.listShareByUserId(request.getUid());
        if (shareDOList != null && shareDOList.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            for (ShareDO shareDO : shareDOList) {
                map.put(i++ + "", JSONUtils.toJSONString(shareDO));
            }
            return RestAPIResultDTO.Success(map);
        } else {
            return RestAPIResultDTO.Success("成功");
        }
    }

    /**
     * 取消分享数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<String> unShareHandle(String uid, String shareIds) {
        List<String> shareIdList = JSONUtils.parseObject(shareIds, List.class);
        shareDao.removeShareByShareIdList(uid, shareIdList);
        shareMapDao.removeShareMapByShareIdList(shareIdList);
        return RestAPIResultDTO.Success("取消分享成功");
    }

    /**
     * 获取分享用户信息数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<Map<String, Object>> getShareUserHandle(String shareId) {
        RestAPIResultDTO<String> panResult = new RestAPIResultDTO<>();
        ShareDTO ShareDTO = new ShareDTO();
        ShareDO shareDO = shareDao.getShareByShareId(shareId);
        if (shareDO == null) {
            return RestAPIResultDTO.Success(null);
        }
        BeanUtils.copyPropertiesIgnoreNull(shareDO, ShareDTO);
        UserInfoDTO userInfoDTO = userRemote.getUserInfo(shareDO.getUserId()).getRespData();
        Map<String, Object> map = new HashMap<>();
        map.put("userinfo", JSONUtils.toJSONString(userInfoDTO));
        map.put("share", JSONUtils.toJSONString(ShareDTO));
        return RestAPIResultDTO.Success(map);
    }

    /**
     * 保存分享数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<String> saveShareHandle(SaveShareRequest request) {
        if (verifyLock(request.getLockPassword(), request.getShareId())) {
            List<ShareMapDO> shareMapDOList = shareMapDao.listShareMapByShareId(request.getShareId());
            if (shareMapDOList != null && shareMapDOList.size() > 0) {
                for (ShareMapDO shareMapDO : shareMapDOList) {
                    VirtualAddressDTO virtualAddressDTO = coreRemote.getVirtualaddress(shareMapDO.getVirtualAddressId(), request.getUid()).getRespData();
                    CreateVirtualAddressRequest createVirtualAddressRequest = new CreateVirtualAddressRequest();
                    createVirtualAddressRequest.setUid(request.getUid());
                    createVirtualAddressRequest.setParentPath(request.getDest());
                    createVirtualAddressRequest.setMd5(virtualAddressDTO.getFileMd5());
                    createVirtualAddressRequest.setFileType(virtualAddressDTO.getAddrType() + "");
                    createVirtualAddressRequest.setFileSizem(virtualAddressDTO.getFileSize() + "");
                    createVirtualAddressRequest.setFileName(virtualAddressDTO.getFileName());
                    createVirtualAddressRequest.setFid(virtualAddressDTO.getFileId());
                    Integer result = coreRemote.createVirtualAddress(createVirtualAddressRequest).getRespData();
                    if (result.equals(0)) {
                        return RestAPIResultDTO.Error("保存失败，容量不足");
                    }
                }
                ShareDO shareDO = shareDao.getShareByShareId(request.getShareId());
                shareDO.setSaveCount(shareDO.getSaveCount() + 1);
                shareDao.updateShare(shareDO);
                return RestAPIResultDTO.Success("成功");
            }
            return RestAPIResultDTO.Error("分享失效");
        }
        return RestAPIResultDTO.Error("提取密码不正确");
    }

    /**
     * 验证分享密码
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    private boolean verifyLock(String lockPassword, String shareId) {
        ShareDO shareDO = shareDao.getShareByShareId(shareId);
        if (shareDO != null) {
            if (shareDO.getSharePassword().equals(lockPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询分享是否带密码数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<String> checkLockHandle(String shareId) {
        ShareDO shareDO = shareDao.getShareByShareId(shareId);
        if (shareDO != null) {
            if (shareDO.getLockWhether().equals(1)) {
                return RestAPIResultDTO.Success("Lock", "成功");
            }
            return RestAPIResultDTO.Success("unLock", "成功");
        }
        return RestAPIResultDTO.Error("分享信息不存在");
    }

    /**
     * 验证分享密码数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<String> verifykLockHandle(String lockPassword, String shareId) {
        if (verifyLock(lockPassword, shareId)) {
            return RestAPIResultDTO.Success("成功");
        } else {
            return RestAPIResultDTO.Error("失败");
        }
    }

    /**
     * 根据分享ID获取虚拟地址ID数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public RestAPIResultDTO<Map<String, Object>> getVinfoHandle(String shareId, String lockPassword) {
        Map<String, Object> map = new HashMap<>();
        if (verifyLock(lockPassword, shareId)) {
            List<ShareMapDO> shareMapDOList = shareMapDao.listShareMapByShareId(shareId);
            List<String> vids = new ArrayList<>();
            if (shareMapDOList != null && shareMapDOList.size() > 0) {
                for (ShareMapDO shareMapDO : shareMapDOList) {
                    vids.add(shareMapDO.getVirtualAddressId());
                    map.put("vid", vids);
                }
            }
            ShareDO shareDO = shareDao.getShareByShareId(shareId);
            map.put("uid", shareDO.getUserId());
            return RestAPIResultDTO.Success(map);
        }
        return RestAPIResultDTO.Error("密码验证失败");
    }

    /**
     * 增加分享访问量数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public void addShareViewCountHandle(AddShareViewCountRequest request) {
        ShareDO shareDO = shareDao.getShareByShareId(request.getShareId());
        if (shareDO != null) {
            shareDO.setVisitCount(shareDO.getVisitCount() + 1);
        }
        shareDao.updateShare(shareDO);
    }

    /**
     * 增加分享下载量数据处理
     *
     * @author: quhailong
     * @date: 2019/9/26
     */
    @Override
    public void addShareDownloadCountHandle(String shareId) {
        ShareDO shareDO = shareDao.getShareByShareId(shareId);
        if (shareDO != null) {
            shareDO.setDownloadCount(shareDO.getDownloadCount() + 1);
        }
        shareDao.updateShare(shareDO);
    }
}

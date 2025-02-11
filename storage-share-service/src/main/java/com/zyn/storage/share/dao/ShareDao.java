package com.zyn.storage.share.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.zyn.storage.share.entity.ShareDO;

import java.util.List;

@Component
public interface ShareDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareDO record);

    int insertSelective(ShareDO record);

    ShareDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareDO record);

    int updateByPrimaryKey(ShareDO record);

    /**
     * 保存分享信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    Integer saveShare(ShareDO shareDO);

    /**
     * 获取分享列表根据用户ID
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    List<ShareDO> listShareByUserId(@Param("userId") String userId);

    /**
     * 删除分享根据多个分享ID
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    Integer removeShareByShareIdList(@Param("userId") String userId, @Param("shareIdList") List<String> shareIdList);

    /**
     * 根据分享ID获取分享信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    ShareDO getShareByShareId(@Param("shareId") String shareId);

    /**
     * 更新分享信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    Integer updateShare(ShareDO shareDO);
}
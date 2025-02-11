package com.zyn.storage.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.zyn.storage.user.entity.UserInfoDO;

@Repository
public interface UserInfoDao {
    /**
     * 根据登录凭证获取用户信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    UserInfoDO getUserInfoByPassport(@Param("passport") String passport);

    /**
     * 根据用户名或手机号获取用户信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    UserInfoDO getUserInfoByUserNameOrPhone(@Param("userName") String userName, @Param("phone") String phone);

    /**
     * 保存用户信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/25
     */
    Integer saveUserInfo(UserInfoDO userInfoDO);

    /**
     * 更新用户信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    Integer updateUserInfo(UserInfoDO userInfoDO);

    /**
     * 根据用户ID获取用户信息
     *
     * @author: zhouyongnan
     * @date: 2024/9/26
     */
    UserInfoDO getUserInfoByUserId(@Param("userId") String userId);
}
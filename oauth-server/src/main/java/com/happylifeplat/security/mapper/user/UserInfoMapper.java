package com.happylifeplat.security.mapper.user;

import com.happylifeplat.security.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/1 15:20</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface UserInfoMapper {

    /**
     * 用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 通过用户ID查询用户是否为业主（消费者）
     *
     * @param userId 用户ID
     * @return 布尔值
     */
    Boolean isOwn(@Param("userId") String userId);

    /**
     * 通过用户ID查询用户是否为供应商
     *
     * @param userId 用户ID
     * @return 布尔值
     */
    Boolean isProvider(@Param("userId") String userId);

}

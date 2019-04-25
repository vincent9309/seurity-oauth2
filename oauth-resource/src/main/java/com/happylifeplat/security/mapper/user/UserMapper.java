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
public interface UserMapper {

    /**
     * 用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User selectByUsername(@Param("username") String username);

}

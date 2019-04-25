package com.happylifeplat.security.mapper.user;

import com.happylifeplat.security.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/8 15:19</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface RoleInfoMapper {

    /**
     * 通过用户ID查询用户角色
     *
     * @param userId 用户ID
     * @return 用户角色集合
     */
    List<Role> selectByUserId(@Param("userId") String userId);
}

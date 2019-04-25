package com.happylifeplat.security.mapper.oauth;

import com.happylifeplat.security.model.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/19 19:33</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface ResourceMapper {

    /**
     * 添加资源
     *
     * @param resource 资源
     */
    void add(Resource resource);

    /**
     * 通过资源名查询资源
     *
     * @param name 资源名
     * @return 资源
     */
    Resource selectByName(@Param("name") String name);

    /**
     * 通过资源ID查询资源
     * @param resourceId 资源ID
     * @return 资源
     */
    Resource selectById(@Param("resourceId") String resourceId);

    /**
     * 条件查询资源
     * @param resource 条件
     * @return 资源列表
     */
    List<Resource> selectByCondition(Resource resource);

    /**
     * 通过资源ID删除资源
     * @param resourceId 资源ID
     */
    void delete(@Param("resourceId") String resourceId);
}

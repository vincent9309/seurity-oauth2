package com.happylifeplat.security.service;

import com.github.pagehelper.PageInfo;
import com.happylifeplat.security.model.Resource;

import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/20 9:53</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface ResourceService {

    /**
     * 资源注册
     *
     * @param resource 资源
     * @return 资源
     */
    Resource registerResource(Resource resource);

    /**
     * 分页条件查询资源
     *
     * @param resource 条件
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return
     */
    PageInfo<Resource> findPage(Resource resource, Integer pageNum, Integer pageSize);

    /**
     * 条件查询资源
     *
     * @param resource 条件
     * @return 资源列表
     */
    List<Resource> findList(Resource resource);

    /**
     * 删除资源
     * @param resourceId 资源ID
     */
    void delete(String resourceId);

}

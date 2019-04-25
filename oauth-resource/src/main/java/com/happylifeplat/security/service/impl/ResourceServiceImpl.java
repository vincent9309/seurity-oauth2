package com.happylifeplat.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happylifeplat.security.common.GuidGenerator;
import com.happylifeplat.security.exception.ResourceException;
import com.happylifeplat.security.mapper.oauth.ResourceMapper;
import com.happylifeplat.security.model.Resource;
import com.happylifeplat.security.service.ResourceService;
import com.happylifeplat.security.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/20 9:59</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional
    public Resource registerResource(Resource resource) {

        String name = resource.getName();
        Resource history = resourceMapper.selectByName(name);
        if (history != null) {
            logger.error("{} 资源已经存在！", name);
            throw new ResourceException(name + "资源已经存在！");
        }
        String resourceId = GuidGenerator.generate();
        resource.setId(resourceId);
        resource.setDisabled(Boolean.FALSE);
        Date date = DateUtils.getCurrentDate();
        resource.setUpdateTime(date);
        resource.setCreateTime(date);
        resourceMapper.add(resource);
        return resource;

    }

    @Override
    public PageInfo<Resource> findPage(Resource resource, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Resource> resourceList = resourceMapper.selectByCondition(resource);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        return pageInfo;
    }

    @Override
    public List<Resource> findList(Resource resource) {
        List<Resource> resourceList = resourceMapper.selectByCondition(resource);
        return resourceList;
    }

    @Override
    public void delete(String resourceId) {
        Resource resource = resourceMapper.selectById(resourceId);
        if (resource == null) {
            logger.warn("资源：{}不存在！", resourceId);
            throw new ResourceException("资源：" + resourceId + "不存在");
        }
        resourceMapper.delete(resourceId);
    }
}

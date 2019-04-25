package com.happylifeplat.security.controller;

import com.github.pagehelper.PageInfo;
import com.happylifeplat.Result;
import com.happylifeplat.messagecode.impl.CommonCode;
import com.happylifeplat.messagecode.impl.OauthServerCode;
import com.happylifeplat.security.model.Resource;
import com.happylifeplat.security.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/19 19:08</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    /**
     * 注册资源
     *
     * @param resource 资源
     * @return result
     */
    @PostMapping("/register")
    public Result register(@RequestBody Resource resource) {
        String name = resource.getName();
        if (StringUtils.isBlank(name)) {
            return new Result(OauthServerCode.resource_name_not_found);
        } else {
            try {
                resourceService.registerResource(resource);
                return new Result(CommonCode.sussess, resource);
            } catch (Exception re) {
                logger.error("资源注册失败！", re);
                OauthServerCode apiCode = OauthServerCode.resource_register_fail;
                apiCode.setMessage(re.getMessage());
                return new Result(apiCode);
            }
        }
    }

    /**
     * 资源分页查询
     *
     * @param name     资源名称
     * @param alias    别名
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return result
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String alias,
                       @RequestParam(defaultValue = "0") Integer pageNum,
                       @RequestParam(defaultValue = "20") Integer pageSize) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setAlias(alias);
        PageInfo<Resource> pageInfo = resourceService.findPage(resource, pageNum, pageSize);
        return new Result(CommonCode.sussess, pageInfo);
    }

    /**
     * 条件查询资源
     *
     * @param name  资源名称
     * @param alias 别名
     * @return result
     */
    @GetMapping("/list")
    public Result list(@RequestParam String name,
                       @RequestParam String alias) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setAlias(alias);
        List<Resource> resourceList = resourceService.findList(resource);
        return new Result(CommonCode.sussess, resourceList);
    }

    /**
     * 删除资源
     * @param id 资源ID
     * @return result
     */
    @PostMapping("/{id}/delete")
    public Result delete(@PathVariable String id){
        try {
            resourceService.delete(id);
            return new Result(CommonCode.sussess);
        } catch (RuntimeException re) {
            logger.error("资源删除失败！", re);
            OauthServerCode apiCode = OauthServerCode.resource_delete_fail;
            apiCode.setMessage(re.getMessage());
            return new Result(apiCode);
        }
    }
}

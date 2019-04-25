package com.happylifeplat.security.controller;

import com.github.pagehelper.PageInfo;
import com.happylifeplat.Result;
import com.happylifeplat.messagecode.impl.CommonCode;
import com.happylifeplat.messagecode.impl.OauthServerCode;
import com.happylifeplat.security.model.Client;
import com.happylifeplat.security.model.ClientInfo;
import com.happylifeplat.security.model.OauthClientDetails;
import com.happylifeplat.security.service.ClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/15 14:13</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private static final String rolePrefix = "ROLE_";

    @Autowired
    private ClientService clientService;

    /**
     * 客户端注册
     *
     * @param clientInfo 客户端
     * @return result
     */
    @PostMapping("/register")
    public Result register(@RequestBody ClientInfo clientInfo) {
        if (StringUtils.isBlank(clientInfo.getName())) {
            logger.warn("客户端名称为空！");
            return new Result(OauthServerCode.client_name_not_found);
        }
        if (CollectionUtils.isEmpty(clientInfo.getResourceIds())) {
            logger.warn("客户端可访问资源为空！");
            return new Result(OauthServerCode.client_resource_not_found);
        }
        if (CollectionUtils.isEmpty(clientInfo.getAuthorizedGrantTypes())) {
            logger.warn("客户端可认证方式为空！");
            return new Result(OauthServerCode.client_grant_types_not_found);
        }
        if (CollectionUtils.isEmpty(clientInfo.getAuthorities())) {
            logger.warn("客户端拥有权限为空！");
            return new Result(OauthServerCode.client_authorities_not_found);
        } else {
            Set<String> temp = clientInfo.getAuthorities();
            Set<String> authorities = new HashSet<>();
            temp.stream().forEach(authority -> {
                authorities.add(rolePrefix + authority);
            });
            clientInfo.setAuthorities(authorities);
        }

        /**
         * 默认 true
         */
        if (CollectionUtils.isEmpty(clientInfo.getAutoapprove())) {
            Set<String> autoapprove = new HashSet<>();
            autoapprove.add("true");
            clientInfo.setAutoapprove(autoapprove);
        }

        try {
            OauthClientDetails clientDetails = clientService.registerClient(clientInfo);
            return new Result(CommonCode.sussess, clientDetails);
        } catch (RuntimeException ex) {
            logger.error("注册客户端失败！", ex);
            OauthServerCode apiCode = OauthServerCode.client_register_fail;
            apiCode.setMessage(ex.getMessage());
            return new Result(apiCode);
        }
    }

    /**
     * 客户端分页查询
     *
     * @param clientId   客户端ID
     * @param clientName 客户端名称
     * @param pageNum    页码
     * @param pageSize   页大小
     * @return result
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "") String clientId,
                       @RequestParam(defaultValue = "") String clientName,
                       @RequestParam(defaultValue = "0") Integer pageNum,
                       @RequestParam(defaultValue = "20") Integer pageSize) {
        Client client = new Client();
        client.setId(clientId);
        client.setName(clientName);

        PageInfo<OauthClientDetails> pageInfo = clientService.findPage(client, pageNum, pageSize);
        return new Result(CommonCode.sussess, pageInfo);
    }

    /**
     * 修改客户端信息
     *
     * @param clientId   客户端id
     * @param clientInfo 客户端信息
     * @return result
     */
    @PostMapping("/update/{clientId}")
    public Result update(@PathVariable("clientId") String clientId,
                         @RequestBody ClientInfo clientInfo) {
        try {
            if (!CollectionUtils.isEmpty(clientInfo.getAuthorities())) {
                Set<String> temp = clientInfo.getAuthorities();
                Set<String> authorities = new HashSet<>();
                temp.stream().forEach(authority -> {
                    authorities.add(rolePrefix + authority);
                });
                clientInfo.setAuthorities(authorities);
            }
            OauthClientDetails clientDetails = clientService.updateClient(clientId, clientInfo);
            return new Result(CommonCode.sussess, clientDetails);
        } catch (RuntimeException ex) {
            logger.error("修改客户端失败！", ex);
            OauthServerCode apiCode = OauthServerCode.client_update_fail;
            apiCode.setMessage(ex.getMessage());
            return new Result(apiCode);
        }
    }

    /**
     * 刷新客户端密码
     *
     * @param clientId 客户端ID
     * @return result
     */
    @PostMapping("/change_secret")
    public Result changeSecret(@RequestParam String clientId) {
        try {
            String secret = clientService.changeSecret(clientId);
            return new Result(CommonCode.sussess, secret);
        } catch (RuntimeException ex) {
            logger.error("修改客户端密码失败！", ex);
            OauthServerCode apiCode = OauthServerCode.client_update_secret_fail;
            apiCode.setMessage(ex.getMessage());
            return new Result(apiCode);
        }
    }

    /**
     * 删除客户端
     * @param clientId 客户端ID
     * @return result
     */
    @PostMapping("/{clientId}/delete")
    public Result delete(@PathVariable String clientId){
        try {
            clientService.delete(clientId);
            return new Result(CommonCode.sussess);
        } catch (RuntimeException re) {
            logger.error("删除客户端失败！", re);
            OauthServerCode apiCode = OauthServerCode.client_delete_fail;
            apiCode.setMessage(re.getMessage());
            return new Result(apiCode);
        }
    }

}

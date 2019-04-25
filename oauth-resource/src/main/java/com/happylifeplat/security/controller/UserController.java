package com.happylifeplat.security.controller;

import com.happylifeplat.Result;
import com.happylifeplat.messagecode.impl.CommonCode;
import com.happylifeplat.messagecode.impl.OauthServerCode;
import com.happylifeplat.security.model.User;
import com.happylifeplat.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/21 18:15</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public Principal info(Principal principal) {
        return principal;
    }

    @RequestMapping("/me")
    public Principal me(Principal principal) {
        String username = principal.getName();
        if (StringUtils.isBlank(username)) {
            logger.warn("用户名为空！");
            throw new UsernameNotFoundException("用户名为空！");
        }
        try {
            User user = userService.getByName(username);
            return user;
        } catch (Exception ex) {
            logger.error("查询用户信息失败！", ex);
            throw new UsernameNotFoundException("查询用户信息失败！", ex);
        }
    }

    @RequestMapping("/detail")
    public Result detail(Principal principal) {
        String username = principal.getName();
        if (StringUtils.isBlank(username)) {
            logger.warn("用户名为空！");
            return new Result(OauthServerCode.username_not_found);
        }
        try {
            User user = userService.getByName(username);
            return new Result(CommonCode.sussess, user);
        } catch (Exception ex) {
            logger.error("查询用户信息失败！", ex);
            OauthServerCode apiCode = OauthServerCode.userinfo_select_fail;
            apiCode.setMessage(ex.getMessage());
            return new Result(apiCode);
        }
    }
}

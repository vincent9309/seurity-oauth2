package com.happylifeplat.security.controller;

import com.happylifeplat.Result;
import com.happylifeplat.messagecode.impl.CommonCode;
import com.happylifeplat.security.model.Authorities;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/25 16:52</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    /**
     * 权限列表
     *
     * @return result
     */
    @GetMapping("/list")
    public Result list() {
        Authorities[] authoritiesList = Authorities.values();
        return new Result(CommonCode.sussess, authoritiesList);
    }
}
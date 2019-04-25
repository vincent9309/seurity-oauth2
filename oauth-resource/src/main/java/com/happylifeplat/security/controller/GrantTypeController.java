package com.happylifeplat.security.controller;

import com.happylifeplat.Result;
import com.happylifeplat.messagecode.impl.CommonCode;
import com.happylifeplat.security.model.GrantTypes;
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
@RequestMapping("/grant_type")
public class GrantTypeController {

    /**
     * 授权方式
     *
     * @return result
     */
    @GetMapping("/list")
    public Result list() {
        GrantTypes[] grantTypes = GrantTypes.values();
        return new Result(CommonCode.sussess, grantTypes);
    }
}

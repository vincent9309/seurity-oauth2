package com.happylifeplat.security.service.impl;

import com.happylifeplat.security.mapper.user.UserMapper;
import com.happylifeplat.security.model.User;
import com.happylifeplat.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/22 16:53</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByName(String username) {
        User user = userMapper.selectByUsername(username);
        if(user == null){
            logger.warn("用户：{} 不存在", username);
            throw new UsernameNotFoundException("用户："+ username + "不存在！");
        }
        return user;
    }
}

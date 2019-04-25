package com.happylifeplat.security.service;

import com.happylifeplat.security.model.User;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/22 16:53</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface UserService {

    User getByName(String username);
}

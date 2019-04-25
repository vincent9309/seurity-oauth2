package com.happylifeplat.security.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/25 10:25</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@JsonSerialize(using = EnumSerializer.class)
public interface EnumView {

    String getName();

    String getValue();
}

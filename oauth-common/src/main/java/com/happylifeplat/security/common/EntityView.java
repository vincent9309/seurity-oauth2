package com.happylifeplat.security.common;

import java.io.Serializable;

/**
 * <p>Description: .</p>
 * <p>Company: </p>
 * <p>Date: 2017/9/6 15:10</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface EntityView<IDClazz extends Serializable> extends Serializable {

    IDClazz getId();

    void setId(IDClazz var1);
}

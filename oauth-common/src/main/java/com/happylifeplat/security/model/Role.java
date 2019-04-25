package com.happylifeplat.security.model;

import com.happylifeplat.security.common.EntityView;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/8 15:17</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class Role implements EntityView<String> {

    private static final long serialVersionUID = -825785011739571693L;

    private String id;

    private String name;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

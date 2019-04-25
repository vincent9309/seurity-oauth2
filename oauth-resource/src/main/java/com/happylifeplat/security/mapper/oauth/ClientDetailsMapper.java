package com.happylifeplat.security.mapper.oauth;

import com.happylifeplat.security.model.Client;
import com.happylifeplat.security.model.OauthClientDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/14 15:59</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface ClientDetailsMapper {

    /**
     * 插入客户端信息
     * @param client 客户端信息
     */
    void insert(Client client);

    /**
     * 通过客户端ID查询客户端
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    OauthClientDetails selectById(@Param("clientId") String clientId);

    /**
     * 查询客户端
     * @param client 查询条件
     * @return 客户端列表
     */
    List<OauthClientDetails> selectList(Client client);

    /**
     * 更新客户端
     * @param client 客户端
     */
    void update(Client client);

    /**
     * 删除客户端
     * @param clientId 客户端ID
     */
    void delete(@Param("clientId") String clientId);

}

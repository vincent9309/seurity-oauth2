package com.happylifeplat.security.service;

import com.github.pagehelper.PageInfo;
import com.happylifeplat.security.model.Client;
import com.happylifeplat.security.model.ClientInfo;
import com.happylifeplat.security.model.OauthClientDetails;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/15 15:44</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface ClientService {

    OauthClientDetails registerClient(ClientInfo clientInfo);

    PageInfo<OauthClientDetails> findPage(Client client, Integer pageNum, Integer pageSize);

    OauthClientDetails updateClient(String clientId, ClientInfo clientInfo);

    String changeSecret(String clientId);

    void delete(String clientId);
}

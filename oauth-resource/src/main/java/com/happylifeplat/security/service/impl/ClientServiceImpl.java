package com.happylifeplat.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happylifeplat.security.common.GuidGenerator;
import com.happylifeplat.security.common.JsonValidator;
import com.happylifeplat.security.exception.ClientException;
import com.happylifeplat.security.mapper.oauth.ClientDetailsMapper;
import com.happylifeplat.security.model.Client;
import com.happylifeplat.security.model.ClientInfo;
import com.happylifeplat.security.model.OauthClientDetails;
import com.happylifeplat.security.service.ClientService;
import com.happylifeplat.security.util.DateUtils;
import com.happylifeplat.security.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/15 15:46</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private ClientDetailsMapper clientDetailsMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OauthClientDetails registerClient(ClientInfo clientInfo) {

        String clientId = GuidGenerator.generate();

        BaseClientDetails clientDetails = transfor(clientInfo);
        clientDetails.setClientId(clientId);
        String secret = GuidGenerator.generateClientSecret();
        String clientSecret = passwordEncoder.encode(secret);
        clientDetails.setClientSecret(clientSecret);
        Map secretMap = new  HashMap<String, String>();
        secretMap.put("secret", secret);
        clientDetails.setAdditionalInformation(secretMap);
        clientRegistrationService.addClientDetails(clientDetails);

        Client client = new Client();
        client.setId(clientId);
        client.setName(clientInfo.getName());
        client.setDisabled(Boolean.FALSE);
        Date date = DateUtils.getCurrentDate();
        client.setUpdateTime(date);
        client.setCreateTime(date);
        clientDetailsMapper.insert(client);

        OauthClientDetails oauthClientDetails = clientDetailsMapper.selectById(clientId);
        oauthClientDetails.setClientSecret(secret);
        return oauthClientDetails;
    }

    @Override
    public PageInfo<OauthClientDetails> findPage(Client client, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OauthClientDetails> oauthClientDetailsList = clientDetailsMapper.selectList(client);
        PageInfo<OauthClientDetails> pageInfo = new PageInfo<>(oauthClientDetailsList);
        return pageInfo;
    }

    @Override
    @Transactional
    public OauthClientDetails updateClient(String clientId, ClientInfo clientInfo) {
        OauthClientDetails oauthClientDetails = clientDetailsMapper.selectById(clientId);
        if (null == oauthClientDetails) {
            logger.warn("客户端:{}不存在！", clientId);
            throw new ClientException("客户端:" + clientId + "不存在！");
        }

        BaseClientDetails clientDetails = updateTransfor(clientInfo);
        clientDetails.setClientId(clientId);
        clientRegistrationService.updateClientDetails(clientDetails);


        // 如果 name为空，或者没有修改，则不更新oauth_client表
        // 如果name不为空，且有修改，则更新oauth_client表
        String name = clientInfo.getName();
        if (StringUtils.isNotBlank(name) && !name.equals(oauthClientDetails.getName())) {
            Client client = new Client();
            client.setId(clientId);
            client.setName(name);
            client.setUpdateTime(DateUtils.getCurrentDate());
        }

        oauthClientDetails = clientDetailsMapper.selectById(clientId);
        return oauthClientDetails;
    }

    @Override
    public String changeSecret(String clientId) {
        String secret = GuidGenerator.generateClientSecret();
        String clientSecret = passwordEncoder.encode(secret);

        clientRegistrationService.updateClientSecret(clientId, clientSecret);
        return secret;
    }

    @Override
    @Transactional
    public void delete(String clientId) {
        OauthClientDetails oauthClientDetails = clientDetailsMapper.selectById(clientId);
        if (null == oauthClientDetails) {
            logger.warn("客户端:{}不存在！", clientId);
            throw new ClientException("客户端:" + clientId + "不存在！");
        }

        clientRegistrationService.removeClientDetails(clientId);

        clientDetailsMapper.delete(clientId);
    }

    private BaseClientDetails transfor(ClientInfo clientInfo) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setResourceIds(clientInfo.getResourceIds());
        clientDetails.setScope(clientInfo.getScope());
        clientDetails.setAuthorizedGrantTypes(clientInfo.getAuthorizedGrantTypes());
        clientDetails.setRegisteredRedirectUri(clientInfo.getWebServerRedirectUri());

        Set<String> authorities = clientInfo.getAuthorities();
        clientDetails.setAuthorities(authoritiesTransfor(authorities));;

        clientDetails.setAccessTokenValiditySeconds(clientInfo.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(clientInfo.getRefreshTokenValidity());

        String additional = clientInfo.getAdditionalInformation();
        clientDetails.setAdditionalInformation(additionalTransfor(additional));

        clientDetails.setAutoApproveScopes(clientInfo.getAutoapprove());

        return clientDetails;
    }

    private BaseClientDetails updateTransfor(ClientInfo clientInfo) {
        BaseClientDetails clientDetails = new BaseClientDetails();

        Set<String> resourceIds = clientInfo.getResourceIds();
        if (CollectionUtils.isNotEmpty(resourceIds)){
            clientDetails.setResourceIds(resourceIds);
        }

        clientDetails.setScope(clientInfo.getScope());

        Set<String> authorizedGrantTypes = clientInfo.getAuthorizedGrantTypes();
        if (CollectionUtils.isNotEmpty(authorizedGrantTypes)) {
            clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        }

        Set<String> registeredRedirectUri = clientInfo.getWebServerRedirectUri();
        if (CollectionUtils.isNotEmpty(registeredRedirectUri)) {
            clientDetails.setRegisteredRedirectUri(registeredRedirectUri);
        }

        Set<String> authorities = clientInfo.getAuthorities();
        clientDetails.setAuthorities(authoritiesTransfor(authorities));;

        clientDetails.setAccessTokenValiditySeconds(clientInfo.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(clientInfo.getRefreshTokenValidity());

        String additional = clientInfo.getAdditionalInformation();
        clientDetails.setAdditionalInformation(additionalTransfor(additional));

        if (CollectionUtils.isEmpty(clientInfo.getAutoapprove())) {
            Set<String> autoapprove = new HashSet<>();
            autoapprove.add("true");
            clientDetails.setAutoApproveScopes(autoapprove);
        } else {
            clientDetails.setAutoApproveScopes(clientInfo.getAutoapprove());
        }

        return clientDetails;
    }

    private List<GrantedAuthority> authoritiesTransfor(Set<String> authorities){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(authorities)){
            for (String authority : authorities) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
            }
        }
        return grantedAuthorityList;
    }

    private Map<String, Object> additionalTransfor(String additional){
        JsonValidator jsonValidator = new JsonValidator();
        if (StringUtils.isNotBlank(additional) && jsonValidator.validate(additional)) {
            try {
                Map<String, Object> additionalMap = JsonUtils.json2Map(additional, String.class, Object.class);
                return additionalMap;
            } catch (IOException e) {
                logger.error("附加条件转换失败", e);
                throw new ClientRegistrationException("附加条件转换失败");
            }
        }
        return new HashedMap();
    }

}

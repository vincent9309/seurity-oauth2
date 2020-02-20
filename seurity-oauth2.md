# seurity-oauth2
Security OAuth 2.0 Authorization Framework

版权声明：本文为CSDN博主「1Vincent」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/wuzhiwei549/article/details/79801789

1.1 角色
OAuth定义了四个角色：

资源所有者
    一个能够授权访问受保护资源的实体。当资源所有者是一个人时，它被称为最终用户。
资源服务器
    托管受保护资源的服务器能够使用访问令牌接受和响应受保护的资源请求。
客户
    代表资源所有者及其授权的应用程序进行受保护的资源请求。术语客户端并不意味着任何特定的实现特征（例如，应用程序是在服务器，台式机还是其他设备上执行的）。
授权服务器
服务器在成功认证资源所有者并获得授权后向客户端发放访问令牌。

 

 

授权服务器和资源服务器之间的交互超出了本规范的范围。授权服务器可以是与资源服务器相同的服务器或独立的实体。一个授权服务器可能会发出多个资源服务器接受的访问令牌。

 

 

 

1.2 协议流程

  图1中所示的抽象OAuth 2.0流程描述了四个角色之间的交互，并包含以下步骤：


（A）
    客户端请求资源所有者的授权。授权请求可以直接给资源所有者（如图所示），或者优选间接地通过授权服务器作为中介。
（B）
    客户端接收授权许可，这是一种代表资源所有者授权的凭证，使用本规范中定义的四种授权类型之一或使用扩展授权类型表示。授权授予类型取决于客户端用于请求授权的方法以及授权服务器支持的类型。
（C）
    客户端通过向授权服务器进行认证并携带授权来请求访问令牌。
（D）
    授权服务器对客户端进行身份验证并验证授权，并且如果有效则发出访问令牌。
（E）
    客户端从资源服务器请求受保护的资源并通过携带访问令牌进行认证。
（F）
    资源服务器验证访问令牌，并且如果有效，则为该请求提供服务。


客户从资源所有者（步骤（A）和（B）中描述）获得授权许可的首选方法是使用授权服务器作为中介

 

 

 

 

2 模式
oauth2根据使用场景不同，分成了4种模式
  ● 客户端模式（client credentials）
  ● 密码模式（resource owner password credentials）
  ● 授权码模式（authorization code）
  ● 简化模式（implicit）

 

 Oauth基于客户端与认证服务器验证的能力定义了两种客户端类型（以及，维护客户端认证信息的能力）: 客户端模式、密码模式。

基础参数定义：
grant_type （发放令牌类型）、
client_id (客户端标识id)
username（用户帐号）
password （用户密码）
client_secret（客户端标识密钥）
refresh_token （刷新令牌）
scope（表示权限范围，可选项）

 

 2.1 客户端模式

　认证服务器给客户端下发客户端标识--一个代表了注册信息的唯一字符串。客户端标识不是秘密；它被暴露给资源拥有者，并且不能单独用来客户端验证。客户端标识对认证服务器来说是唯一的。



（A）客户端直接向认证服务器获取令牌介。

（B）认证服务器确认无误后，向客户端提供访问令牌。
（C） 客户端携带令牌访问资源端

常用于访问公共资源（无需登录）：网站首页

该模式没有refresh_token,过期可以直接认证获取匿名令牌。

样例：



请求参数
grant_type:client_credentials
client_id:46582ae7217343a8b252e3977e7cc421
client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47

结果
{
    "access_token": "9fae1382-8d9c-4c64-a01c-d67817137fd4",
    "token_type": "bearer",
    "expires_in": 27689,
    "scope": "read write"
}

 

2.2 密码端模式

密码模式在客户端基础上，从用户方获取帐号密码，再访问授权服务器认证授权。

 



 

（A）客户端从用户方获取帐号密码。
（B）客户端携带用户信息向认证服务器获取令牌介。
（B）认证服务器确认无误后，向客户端提供访问令牌。

 

（c） 客户端携带令牌访问资源端

常用于访问个人资源（必须登录）：个人资料

样例：



请求参数
grant_type:password
client_id:46582ae7217343a8b252e3977e7cc421
username:18565783136
password:AC1DAdo9ZcY4dKAdtyPRzoICWZlkR7WDgtO06S5fVCUS6A/67rMxeW+2mKKbo2N1FQ==
client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47

结果
{
    "access_token": "41d74d86-bd30-4935-a6f1-c61614a1b72b",
    "token_type": "bearer",
    "refresh_token": "1ba402f7-394b-420b-9805-39578d6176f8",
    "expires_in": 30063,
    "scope": "read write"

}

 

2.3 授权码模式

授权码模式（authorization code）是功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动。



 （A）用户访问客户端，后者将前者导向认证服务器（一个获取授权的页面）。
 （B）用户选择是否给予客户端授权。
 （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
 （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。

 （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。

常用于 第三方登录，例如：QQ登录网易音乐。

 

样例：

GET  oauth-server/authorize?response_type=code&client_id=cgGvf5Rotv7D76m9JaArfY3YG6fDec47&state=userId
&redirect_uri=www.baidu.com

redirect_uri：表示重定向URL编码后的URI（第三方服务器）

state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。（常用：用户标识）

重定向：
www.baidu.com?code=SplxlOBeZQQYbYS6WxSbIA&state=userId

获取code
GET oauth-server/grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=www.baidu.com

结果
{
    "access_token": "41d74d86-bd30-4935-a6f1-c61614a1b72b",
    "token_type": "bearer",
    "refresh_token": "1ba402f7-394b-420b-9805-39578d6176f8",
    "expires_in": 30063,
    "scope": "read write"

}

2.4 简化模式

不通过第三方应用程序的服务器，直接在浏览器中向认证服务器申请令牌，跳过了"授权码"这个步骤，即没有code，直接返回令牌。



（A）客户端将用户导向认证服务器。
（B）用户决定是否给于客户端授权。
（C）假设用户给予授权，认证服务器将用户导向客户端指定的"重定向URI"，并在URI的Hash部分包含了访问令牌。

（D）客户端（第三方服务器）获取到令牌。

 

样例：

GET  oauth-server/authorize?response_type=token&client_id=cgGvf5Rotv7D76m9JaArfY3YG6fDec47&state=userId

        &redirect_uri=www.baidu.com

重定向：

www.baidu.com?access_token=41d74d86-bd30-4935-a6f1-c61614a1b72b&state=userId

 

 

3 刷新令牌
当访问令牌过期时候，刷新重新获取令牌。

样例：



请求参数
grant_type:refresh_token
client_id:46582ae7217343a8b252e3977e7cc421
client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47
refresh_token:1ba402f7-394b-420b-9805-39578d6176f8


结果
{
    "access_token": "6d8cffd1-a90e-4846-838f-176050ed49b4",
    "token_type": "bearer",
    "refresh_token": "1ba402f7-394b-420b-9805-39578d6176f8",
    "expires_in": 43199,
    "scope": "read write"
}

 

依赖
pom引入security-oauth2依赖

 

 

<dependency>
	<groupId>com.happylifeplat</groupId>
	<artifactId>security-crypto</artifactId>
	<version>1.0-RELEASE</version>
</dependency>
 
 
<dependency>
	<groupId>org.springframework.security.oauth</groupId>
	<artifactId>spring-security-oauth2</artifactId>
</dependency>
案例基于JdbcTokenStore,需要配置数据库

CREATE TABLE `oauth_access_token` (
  `authentication_id` varchar(128) NOT NULL COMMENT '身份验证ID',
  `token_id` varchar(128) DEFAULT NULL COMMENT '令牌ID',
  `token` blob COMMENT '令牌',
  `user_name` varchar(256) DEFAULT NULL COMMENT '用户名',
  `client_id` varchar(128) DEFAULT NULL COMMENT '客户端ID',
  `authentication` blob COMMENT '认证体',
  `refresh_token` varchar(256) DEFAULT NULL COMMENT '刷新令牌',
  PRIMARY KEY (`authentication_id`),
  KEY `PK_token_id` (`token_id`) USING BTREE,
  KEY `PK_refresh_token` (`refresh_token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 
CREATE TABLE `oauth_approvals` (
  `userId` varchar(128) DEFAULT NULL,
  `clientId` varchar(128) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
 
CREATE TABLE `oauth_client` (
  `id` varchar(128) NOT NULL COMMENT '客户端id',
  `name` varchar(256) DEFAULT NULL COMMENT '客户端名称',
  `disabled` bit(1) NOT NULL DEFAULT b'0',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端表';
 
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '客户端所能访问的资源id集合',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端访问密匙',
  `scope` varchar(256) DEFAULT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '客户端重定向URI',
  `authorities` varchar(256) DEFAULT NULL COMMENT '客户端权限',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'access_token的有效时间（单位:秒）',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT 'refresh_token的有效时间（单位:秒）',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '预留字段，JSON格式',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '否自动Approval操作',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端详情';
 
CREATE TABLE `oauth_client_token` (
  `authentication_id` varchar(128) NOT NULL COMMENT '身份验证ID',
  `token_id` varchar(128) DEFAULT NULL COMMENT '令牌ID',
  `token` blob COMMENT '令牌',
  `user_name` varchar(256) DEFAULT NULL COMMENT '用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端ID',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
 
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(128) DEFAULT NULL COMMENT '令牌ID',
  `token` blob COMMENT '令牌',
  `authentication` blob COMMENT '认证体',
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1539 DEFAULT CHARSET=utf8mb4;
 
CREATE TABLE `oauth_resource` (
  `id` varchar(128) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `alias` varchar(256) DEFAULT NULL,
  `describe` varchar(256) DEFAULT NULL,
  `disabled` bit(1) NOT NULL DEFAULT b'0',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';


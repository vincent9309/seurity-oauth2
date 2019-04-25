-- 资源
INSERT INTO oauth_resource (id, `name`, alias, `describe`, disabled, update_time, create_time)
VALUES('64b54de255c34c95abaeb1dbc40dec93', 'WGJ_OAUTH_RESOURCE', 'oauth资源服务器', 'oauth资源服务器', 0, '2017-9-25 00:00:00', '2017-9-25 00:00:00');

INSERT INTO oauth_resource (id, `name`, alias, `describe`, disabled, update_time, create_time)
VALUES('fdfd9674130740218b4a8dd523ad13ad', 'APP_BACKGROUND_RESOURCE', 'app后台服务', 'app后台服务', 0, '2017-9-25 00:00:00', '2017-9-25 00:00:00');

INSERT INTO oauth_resource (id, `name`, alias, `describe`, disabled, update_time, create_time)
VALUES('080ca2a8a02740328ca2a8a0273032f3', 'BACKGROUND_MANAGE_RESOURCE', 'background后台服务', 'background后台服务', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');

INSERT INTO oauth_resource (id, `name`, alias, `describe`, disabled, update_time, create_time)
VALUES('f71ac319d66c466698a61b530c302982', 'COUPON_RESOURCE', '卡券平台服务', '卡券平台服务', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');

INSERT INTO oauth_resource (id, `name`, alias, `describe`, disabled, update_time, create_time)
VALUES('a9e28ef2666545708bc97172fee458ae', 'WECHAT_RESOURCE', '微信平台服务', '微信平台服务', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');

-- 客户端
INSERT INTO `oauth_client` VALUES ('9302e32e33b649ec8edcee4fe34b043d', '测试资源端', 0, '2017-9-25 00:00:00', '2017-9-25 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('9302e32e33b649ec8edcee4fe34b043d', 'WGJ_OAUTH_RESOURCE', 'AKGOXauSTycl9L1UrIwHcLTeZeCvOE6ZRVzj1kSLzOlBxjrXvTUEB23Un69eg74fPA==', 'write,read', 'client_credentials,password,authorization_code', NULL, 'ROLE_USER,ROLE_ADMIN', 43200, 2592000, '{"secret":"NuTdqR88z4TBUvnRsdb4MOgtxf3sQu44"}', 'true');

INSERT INTO `oauth_client` VALUES ('946057de04c3441dbbf98d13333adf0b', '测试客户端', 0, '2017-9-25 00:00:00', '2017-9-25 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('946057de04c3441dbbf98d13333adf0b', 'WGJ_OAUTH_RESOURCE', 'AHQYwnh7TfFlgFqFtkARJz0tca55BsfnS3dHpcZFcynul+pp/YEvdkrMTjAZU+2HeA==', 'read,write', 'client_credentials,password,authorization_code', 'http://localhost:9999/client', 'ROLE_USER', 43200, 2592000, '{"secret":"iQoCSATbMD1LPsTpozQp85K1s5pDbUkG"}', 'true');

INSERT INTO `oauth_client` VALUES ('cebbdd36546f41d48a0363fc90d48aa3', 'BACKGROUND客户端', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('cebbdd36546f41d48a0363fc90d48aa3', 'BACKGROUND_MANAGE_RESOURCE,WGJ_OAUTH_RESOURCE,APP_BACKGROUND_RESOURCE,WECHAT_RESOURCE,COUPON_RESOURCE', 'ANoQLorDAZFuzzQ6e+gvxb06LkGYo1SdVRN7BI2aKHLeXPmxHvTS4tOgCjV5mE/t5w==', 'write,read', 'client_credentials,password,authorization_code,refresh_token', NULL, 'ROLE_USER,ROLE_MOBILE', 43200, 2592000, '{"secret":"x09Lha46Bn7IHCZWdWzwVEy4Wmrl7F7f"}', 'true');

INSERT INTO `oauth_client` VALUES ('f71026977fee4cefbf4d060bdfd05c1b', 'APP客户端', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('f71026977fee4cefbf4d060bdfd05c1b', 'APP_BACKGROUND_RESOURCE,COUPON_RESOURCE', 'AIelW13a3R5N0JYtH6P+HXJoiqW2emxBL6fT7T/vCUgWtcn+dPZ1nmdPtD+jlWedkg==', 'write,read', 'client_credentials,password,authorization_code,refresh_token', NULL, 'ROLE_USER,ROLE_MOBILE', 43200, 2592000, '{"secret":"ynKX6wB9vaG1e7oC78SSxat2In6NRgMC"}', 'true');

INSERT INTO `oauth_client` VALUES ('46582ae7217343a8b252e3977e7cc421', 'H5客户端', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('46582ae7217343a8b252e3977e7cc421', 'APP_BACKGROUND_RESOURCE,COUPON_RESOURCE,WECHAT_RESOURCE', 'AOj0v7K6vxciW21UwULDhP9NlZCSnpn4SAqKZiWQWAgFOp60xOWWRIBodx/rAFhPwA==', 'write,read', 'client_credentials,password,authorization_code,refresh_token', NULL, 'ROLE_USER,ROLE_MOBILE', 43200, 2592000, '{"secret":"cgGvf5Rotv7D76m9JaArfY3YG6fDec47"}', 'true');

INSERT INTO `oauth_client` VALUES ('872a7a9937664c448b4d3a769859dce0', '小程序', 0, '2017-10-10 00:00:00', '2017-10-10 00:00:00');
INSERT INTO `oauth_client_details` VALUES ('872a7a9937664c448b4d3a769859dce0', 'APP_BACKGROUND_RESOURCE,COUPON_RESOURCE,WECHAT_RESOURCE', 'AMy/6MS0/XHvrjhjPolNZ9mhbrvbF/vsl4ElMAYekG/6fE6eP4w4sCvaoewudYGMJw==', 'write,read', 'client_credentials,password,authorization_code,refresh_token', NULL, 'ROLE_USER,ROLE_MOBILE', 43200, 2592000, '{"secret":"CKGnOle3XV9uZ9MkzkmOZE7tWYyOik51"}', 'true');


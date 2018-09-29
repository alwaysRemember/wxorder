create table `seller_info`(
  `seller_id` varchar (32) not null,
  `username` varchar (32) not null,
  `password` varchar (32) not null,
  `openid`  varchar (64) not null comment '微信openid',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  primary key (`seller_id`)
) comment "卖家信息表";
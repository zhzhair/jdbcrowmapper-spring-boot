CREATE TABLE `feedback` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `user_name` varchar(25) NOT NULL,
  `mobile` varchar(11) NOT NULL,
  `online_status` varchar(25) CHARACTER SET utf8mb4 NOT NULL COMMENT '上网状态',
  `app_version` varchar(100) NOT NULL,
  `sys_version` varchar(100) NOT NULL,
  `os_type` varchar(25) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `reply_count` int(11) NOT NULL DEFAULT '0',
  `ignore_count` int(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `feedback_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply_type` int(11) DEFAULT NULL,
  `reply_context` varchar(255) DEFAULT NULL,
  `feedback_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY FEEDBACK_REPLY_INDEX(`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

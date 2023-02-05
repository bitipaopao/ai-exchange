use ai_exchange;

DROP TABLE IF EXISTS `sys_client_info`;
CREATE TABLE `sys_client_info` (
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'client_id',
  `env` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'env for client',
  `client_access_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'access key',
  `access_secret` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'access secret',
  `app_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'client name',
  `project_id` int DEFAULT NULL COMMENT 'project id',
  `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'expire time',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`,`env`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='S端客户表';

DROP TABLE IF EXISTS `dwd_ai_request`;
CREATE TABLE dwd_ai_request (
	request_id BIGINT auto_increment NOT NULL,
	uuid varchar(100) NOT NULL,
	ai_key varchar(100) NOT NULL,
	function_id varchar(100) NOT NULL,
	argument varchar(500) NULL,
	argument_md5 varchar(100) NULL,
	response TEXT NULL,
	request_status INT NOT NULL,
	done_time TIMESTAMP NULL,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	PRIMARY KEY (`request_id`),
    UNIQUE KEY `dwd_ai_request_UN` (`uuid`)
)
ENGINE=MyISAM
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `dwd_ai_function`;
CREATE TABLE dwd_ai_function (
	ai_key varchar(100) NOT NULL,
	function_id varchar(100) NOT NULL,
	function_res_type INT DEFAULT '0' NULL,
	function_batch INT DEFAULT '0' NULL,
	flow_interval_second INT DEFAULT '1',
	flow_rate INT DEFAULT '1',
	function_protocol INT DEFAULT '0' NULL,
	function_info TEXT NULL,
	path varchar(500) NULL,
	valid INT DEFAULT '0' NULL,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	PRIMARY KEY (`ai_key`, `function_id`)
)
ENGINE=MyISAM
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `dwd_ai_service`;
CREATE TABLE dwd_ai_service (
	ai_key varchar(100) NOT NULL,
	protocol varchar(100) NOT NULL,
	host varchar(100) NOT NULL,
	port varchar(100) NOT NULL,
	callback_host varchar(200) NULL,
	echo_url varchar(200) NULL,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	PRIMARY KEY (`ai_key`, `host`)
)
ENGINE=MyISAM
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE ai_exchange.dwd_ai_request MODIFY COLUMN argument text NULL;
ALTER TABLE ai_exchange.dwd_ai_request ADD group_step INT NULL after `group_key`;

ALTER TABLE ai_exchange.dwd_ai_request MODIFY COLUMN response MEDIUMTEXT NULL;
ALTER TABLE ai_exchange.dwd_ai_request MODIFY COLUMN argument MEDIUMTEXT NULL;

-- ai_exchange.dwd_course definition

DROP TABLE IF EXISTS `dwd_course`;
CREATE TABLE `dwd_course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `course_kpoint` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `page_number` int DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ai_exchange.dwd_course_page definition

CREATE TABLE `dwd_course_page` (
  `course_id` int NOT NULL,
  `page_sort` int NOT NULL,
  `page_image` mediumtext NOT NULL,
  `description` text,
  PRIMARY KEY (`course_id`,`page_sort`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;
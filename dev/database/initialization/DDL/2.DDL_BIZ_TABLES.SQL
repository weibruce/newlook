
-- biz tables

CREATE TABLE `t_biz_event` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128),
  `store_name` VARCHAR(128),
  `description` VARCHAR(256),
  `start_time` DATETIME,
  `end_time` DATETIME,
  `enabled` TINYINT(1),
  `opening_code` VARCHAR(128),
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_coupon` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128),
  `description` VARCHAR(256),
  `event_oid` BIGINT,
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_rule` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128),
  `description` VARCHAR(256),
  `coupon_oid` BIGINT,
  `event_oid` BIGINT,
  `target_day` DATETIME,
  `priority` INT,
  `weight` INT,
  `probability` DECIMAL(10,4),
  `day_capacity` INT,
  `day_am_capacity` INT,
  `day_pm_capacity` INT,
  `enabled` TINYINT(1),
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_user_coupon` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(256),
  `event_oid` BIGINT,
  `coupon_oid` BIGINT,
  `rule_oid` BIGINT,
  `hit_random_number` DECIMAL(10,4),
  `barcode` VARCHAR(256),
  `content` VARCHAR(512),
  `occur_time` DATETIME,
  `submit_time` DATETIME,
  `start_time` DATETIME,
  `end_time` DATETIME,
  `used` TINYINT(1),
  `used_by` VARCHAR(256),
  `used_occur_time` DATETIME,
  `used_submit_time` DATETIME,
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_accessory` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(256),
  `event_oid` BIGINT,
  `coupon_oid` BIGINT,
  `rule_oid` BIGINT,
  `content` VARCHAR(512),
  `quantity` INT,
  `claimed` TINYINT(1),
  `claimed_time` DATETIME,
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_shared_event` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(256),
  `username` VARCHAR(128),
  `event_oid` BIGINT,
  `shared_occur_time` DATETIME,
  `shared_submit_time` DATETIME,
  `shared_friend_uid` VARCHAR(256),
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);

CREATE TABLE `t_biz_shipping_address` (
  `oid` BIGINT NOT NULL AUTO_INCREMENT,
  `user_coupon_oid` BIGINT,
  `uid` VARCHAR(256),
  `name` VARCHAR(128),
  `cellphone` VARCHAR(32),
  `address` VARCHAR(256),
  `submit_timestamp` DATETIME,
  `sent` TINYINT(1),
  `sent_timestamp` DATETIME,
  `create_by` VARCHAR(128),
  `create_timestamp` DATETIME,
  `update_by` VARCHAR(128),
  `update_timestamp` DATETIME,
  `version` INT NOT NULL DEFAULT 0,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (oid)
);


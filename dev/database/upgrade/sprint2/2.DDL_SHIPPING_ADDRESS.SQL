
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

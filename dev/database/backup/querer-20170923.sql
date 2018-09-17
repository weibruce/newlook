/*
SQLyog Community v12.4.3 (64 bit)
MySQL - 10.2.7-MariaDB-log : Database - querer
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`querer` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `querer`;

/*Table structure for table `t_biz_accessory` */

DROP TABLE IF EXISTS `t_biz_accessory`;

CREATE TABLE `t_biz_accessory` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) DEFAULT NULL,
  `coupon_oid` bigint(20) DEFAULT NULL,
  `rule_oid` bigint(20) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `claimed` tinyint(1) DEFAULT NULL,
  `claimed_time` datetime DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_accessory` */

insert  into `t_biz_accessory`(`oid`,`uid`,`coupon_oid`,`rule_oid`,`content`,`quantity`,`claimed`,`claimed_time`,`create_by`,`create_timestamp`,`update_by`,`update_timestamp`,`version`,`active`) values 
(1,NULL,4,NULL,'9900000104887',999999,0,NULL,NULL,NULL,NULL,NULL,0,1),
(2,NULL,3,NULL,'9900000104863',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(3,NULL,3,NULL,'9900000104856',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(4,NULL,3,NULL,'9900000104849',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(5,NULL,3,NULL,'9900000104832',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(6,NULL,3,NULL,'9900000104825',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(7,NULL,3,NULL,'9900000104818',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(8,NULL,3,NULL,'9900000104801',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(9,NULL,3,NULL,'9900000104795',1,0,NULL,NULL,NULL,NULL,NULL,0,1),

(10,NULL,3,NULL,'9900000104788',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(11,NULL,3,NULL,'9900000104771',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(12,NULL,3,NULL,'9900000104764',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(13,NULL,3,NULL,'9900000104757',1,0,NULL,NULL,NULL,NULL,NULL,0,1),

(14,NULL,3,NULL,'9900000104740',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(15,NULL,3,NULL,'9900000104733',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(16,NULL,3,NULL,'9900000104726',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(17,NULL,3,NULL,'9900000104719',1,0,NULL,NULL,NULL,NULL,NULL,0,1),

(18,NULL,3,NULL,'9900000104702',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(19,NULL,3,NULL,'9900000104870',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(20,NULL,3,NULL,'9900000104696',1,0,NULL,NULL,NULL,NULL,NULL,0,1),
(21,NULL,3,NULL,'9900000104689',1,0,NULL,NULL,NULL,NULL,NULL,0,1);

/*Table structure for table `t_biz_coupon` */

DROP TABLE IF EXISTS `t_biz_coupon`;

CREATE TABLE `t_biz_coupon` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `event_oid` bigint(20) DEFAULT NULL,
  `target_date` datetime DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_coupon` */

insert  into `t_biz_coupon`(`oid`,`name`,`description`,`event_oid`,`target_date`,`enabled`,`create_by`,`create_timestamp`,`update_by`,`update_timestamp`,`version`,`active`) values 
(1,'Luna Mini洗面仪一台',NULL,1,NULL,NULL,'admin','2017-07-22 09:21:16',NULL,NULL,0,1),
(2,'唇膏一支',NULL,1,NULL,NULL,'admin','2017-07-22 09:21:58',NULL,NULL,0,1),
(3,'50元代金券一张',NULL,1,NULL,NULL,'admin','2017-07-22 09:21:58',NULL,NULL,0,1),
(4,'5元代金券一张',NULL,1,NULL,NULL,'admin','2017-07-22 09:21:58',NULL,NULL,0,1),
(9999,'未中奖','谢谢',1,NULL,NULL,'admin','2017-07-22 09:21:58',NULL,NULL,0,1);

/*Table structure for table `t_biz_event` */

DROP TABLE IF EXISTS `t_biz_event`;

CREATE TABLE `t_biz_event` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `store_name` varchar(128) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `opening_code` varchar(128) DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_event` */

insert  into `t_biz_event`(`oid`,`name`,`store_name`,`description`,`start_time`,`end_time`,`enabled`,`opening_code`,`create_by`,`create_timestamp`,`update_by`,`update_timestamp`,`version`,`active`) values 
(1,'上海江桥万达店开业活动','上海江桥万达店','上海江桥万达店','2017-09-23 00:00:01','2017-09-25 23:59:59',1,'nl7mokzukhwhY1NNCP','admin','2017-07-22 08:08:15',NULL,NULL,0,1);

/*Table structure for table `t_biz_rule` */

DROP TABLE IF EXISTS `t_biz_rule`;

CREATE TABLE `t_biz_rule` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `coupon_oid` bigint(20) DEFAULT NULL,
  `event_oid` bigint(20) DEFAULT NULL,
  `target_day` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `probability` decimal(10,4) DEFAULT NULL,
  `day_capacity` int(11) DEFAULT NULL,
  `day_am_capacity` int(11) DEFAULT NULL,
  `day_pm_capacity` int(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_rule` */

insert  into `t_biz_rule`(`oid`,`name`,`description`,`coupon_oid`,`event_oid`,`target_day`,`priority`,`weight`,`probability`,`day_capacity`,`day_am_capacity`,`day_pm_capacity`,`enabled`,`create_by`,`create_timestamp`,`update_by`,`update_timestamp`,`version`,`active`) values 
(1,'R0105001','Luna Mini洗面仪一台',1,1,'2017-09-23 08:00:00',1,1,0.0030,1,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(2,'R0105002','唇膏',2,1,'2017-09-23 08:00:00',2,1,0.0040,1,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(3,'R0105003','50元代金券',3,1,'2017-09-23 08:00:00',3,1,0.0150,6,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(4,'R0105004','5元代金券',4,1,'2017-09-23 08:00:00',4,1,0.9780,999999,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),

(5,'R0105005','Luna Mini洗面仪',1,1,'2017-09-24 08:00:00',1,1,0.0000,0,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(6,'R0105006','唇膏',2,1,'2017-09-24 08:00:00',2,1,0.0040,1,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(7,'R0105007','50元代金券',3,1,'2017-09-24 08:00:00',3,1,0.0140,7,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(8,'R0105008','5元代金券',4,1,'2017-09-24 08:00:00',4,1,0.9820,999999,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),

(9,'R0105009','Luna Mini洗面仪',1,1,'2017-09-25 08:00:00',1,1,0.0000,0,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(10,'R01050010','唇膏',2,1,'2017-09-25 08:00:00',2,1,0.0000,0,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(11,'R01050011','50元代金券',3,1,'2017-09-25 08:00:00',3,1,0.0150,7,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1),
(12,'R01050012','5元代金券',4,1,'2017-09-25 08:00:00',4,1,0.9850,999999,NULL,NULL,1,'admin','2017-07-22 14:58:18',NULL,NULL,0,1);

/*Table structure for table `t_biz_shared_event` */

DROP TABLE IF EXISTS `t_biz_shared_event`;

CREATE TABLE `t_biz_shared_event` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) DEFAULT NULL,
  `username` varchar(128) DEFAULT NULL,
  `event_oid` bigint(20) DEFAULT NULL,
  `shared_occur_time` datetime DEFAULT NULL,
  `shared_submit_time` datetime DEFAULT NULL,
  `shared_friend_uid` varchar(256) DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_shared_event` */

/*Table structure for table `t_biz_user_coupon` */

DROP TABLE IF EXISTS `t_biz_user_coupon`;

CREATE TABLE `t_biz_user_coupon` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) DEFAULT NULL,
  `event_oid` bigint(20) DEFAULT NULL,
  `coupon_oid` bigint(20) DEFAULT NULL,
  `rule_oid` bigint(20) DEFAULT NULL,
  `hit_random_number` decimal(10,4) DEFAULT NULL,
  `barcode` varchar(256) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `occur_time` datetime DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `used` tinyint(1) DEFAULT NULL,
  `used_by` varchar(256) DEFAULT NULL,
  `used_occur_time` datetime DEFAULT NULL,
  `used_submit_time` datetime DEFAULT NULL,
  `create_by` varchar(128) DEFAULT NULL,
  `create_timestamp` datetime DEFAULT NULL,
  `update_by` varchar(128) DEFAULT NULL,
  `update_timestamp` datetime DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_biz_user_coupon` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

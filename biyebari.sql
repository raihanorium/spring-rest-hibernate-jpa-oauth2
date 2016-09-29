/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 10.1.10-MariaDB : Database - biyebari
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`biyebari` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `biyebari`;

/*Table structure for table `agent_promo` */

DROP TABLE IF EXISTS `agent_promo`;

CREATE TABLE `agent_promo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `image_url` varchar(255) NOT NULL,
  `company_id` varchar(50) NOT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `agent_promo` */

insert  into `agent_promo`(`id`,`title`,`image_url`,`company_id`,`is_active`,`created_at`,`updated_at`,`created_by`,`updated_by`) values (1,'agent','','1','active','2016-07-14 12:26:43','2016-07-14 12:26:43','14',NULL),(2,'agent','','1','active','2016-07-14 12:27:22','2016-07-14 12:27:22','14',NULL),(3,'agent','','1','active','2016-07-14 12:28:07','2016-07-14 12:28:07','14',NULL),(4,'agent23w','http://localhost/biyebariadmin/local/storage/app/agent/1468478057-4.jpg','4','active','2016-07-14 12:34:16','2016-07-14 13:36:20','14','14'),(5,'sdfsda','http://localhost/biyebariadmin/local/storage/app/agent/1468482211-5.jpg','1','active','2016-07-14 13:36:39','2016-07-14 13:43:31','14','14');

/*Table structure for table `applicationsettings` */

DROP TABLE IF EXISTS `applicationsettings`;

CREATE TABLE `applicationsettings` (
  `pname` varchar(100) NOT NULL,
  `pvalue` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `applicationsettings` */

insert  into `applicationsettings`(`pname`,`pvalue`) values ('ITEM_IMAGE_PATH','/'),('ITEM_IMAGE_URL_PREFIX','http://localhost/images/'),('API_DATA_REFRESH_TIME','3600'),('VENDOR_LOGO_ABS_PATH','D:\\uploads\\biyebari\\vendorLogos'),('ITEM_IMAGE_ABS_PATH','D:\\uploads\\biyebari\\itemImages'),('VENDOR_LOGO_REL_PATH','http://localhost/uploads/biyebari/vendorLogos'),('ITEM_IMAGE_REL_PATH','http://localhost/uploads/biyebari/itemImages'),('ITEM_IMAGE_THUMB_ABS_PATH','D:\\uploads\\biyebari\\itemImages\\thumbs'),('ITEM_IMAGE_THUMB_REL_PATH','http://localhost/uploads/biyebari/itemImages/thumbs'),('USER_PIC_ABS_PATH','D:\\uploads\\biyebari\\userPics'),('USER_PIC_REL_PATH','http://localhost/uploads/biyebari/userPics');

/*Table structure for table `estimation_review` */

DROP TABLE IF EXISTS `estimation_review`;

CREATE TABLE `estimation_review` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `req_id` varchar(50) NOT NULL,
  `comment` text NOT NULL,
  `is_active` enum('onreview','approved','inactive') DEFAULT 'onreview',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `approved_by_admin` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `estimation_review` */

/*Table structure for table `migrations` */

DROP TABLE IF EXISTS `migrations`;

CREATE TABLE `migrations` (
  `migration` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `migrations` */

/*Table structure for table `password_resets` */

DROP TABLE IF EXISTS `password_resets`;

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  KEY `password_resets_email_index` (`email`),
  KEY `password_resets_token_index` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `password_resets` */

/*Table structure for table `req4estimation` */

DROP TABLE IF EXISTS `req4estimation`;

CREATE TABLE `req4estimation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `budget` varchar(200) NOT NULL COMMENT 'seperate by -',
  `location_id` varchar(50) DEFAULT NULL,
  `season` enum('summer','monsoon','autumn','late_autumn','winter','spring') DEFAULT 'winter',
  `guest_size` varchar(200) DEFAULT NULL,
  `religion` enum('islam','hinduism','buddhism','christianity') DEFAULT 'islam',
  `is_active` enum('requestedbyclient','reviewbyagent','approvebyadmin','inactive') DEFAULT 'requestedbyclient',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `last_updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `req4estimation` */

/*Table structure for table `requested_category` */

DROP TABLE IF EXISTS `requested_category`;

CREATE TABLE `requested_category` (
  `req_id` varchar(50) NOT NULL,
  `category_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `requested_category` */

/*Table structure for table `tbl_area` */

DROP TABLE IF EXISTS `tbl_area`;

CREATE TABLE `tbl_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `a_name` varchar(255) NOT NULL,
  `d_id` int(10) NOT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_area` */

insert  into `tbl_area`(`id`,`a_name`,`d_id`,`is_active`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (2,'Moghbazar',1,'active',NULL,NULL,NULL,NULL),(7,'Banani',1,'active',NULL,NULL,NULL,NULL);

/*Table structure for table `tbl_area_vendor` */

DROP TABLE IF EXISTS `tbl_area_vendor`;

CREATE TABLE `tbl_area_vendor` (
  `vendor_id` bigint(20) unsigned NOT NULL,
  `area_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`vendor_id`,`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_area_vendor` */

/*Table structure for table `tbl_category` */

DROP TABLE IF EXISTS `tbl_category`;

CREATE TABLE `tbl_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(200) NOT NULL,
  `description` text,
  `is_active` enum('active','inactive') DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT '0',
  `c_order` int(11) DEFAULT '0',
  `default_image` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_category` */

insert  into `tbl_category`(`id`,`category_name`,`description`,`is_active`,`group_id`,`parent`,`c_order`,`default_image`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (2,'1','sdfsadfsdads sdf','active',40,0,2,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468389475-2.jpg','2016-07-13 11:55:48','14','2016-07-17 17:57:02','14'),(3,'2','sdfasdfsadfsd','active',40,0,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(4,'3','Updated some description',NULL,NULL,NULL,NULL,NULL,'2016-08-14 14:49:17','1','2016-08-14 14:51:02','1'),(5,'1-1','sdfasdfsadfsd','active',40,2,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(6,'1-1-1','sdfasdfsadfsd','active',40,5,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(7,'1-2','sdfasdfsadfsd','active',40,2,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(8,'2-1','sdfasdfsadfsd','active',40,3,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(9,'2-2','sdfasdfsadfsd','active',40,3,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(10,'2-3','sdfasdfsadfsd','active',40,3,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(11,'2-2-2','sdfasdfsadfsd','active',40,9,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(12,'2-2-1','sdfasdfsadfsd','active',40,9,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(13,'2-2-2-1','sdfasdfsadfsd','active',40,11,1,'http://localhost/biyebariadmin/local/storage/app/Group/category/1468390045-3.jpg','2016-07-13 12:07:10','14','2016-07-17 17:57:02','14'),(14,'Awesome category',NULL,'active',NULL,NULL,NULL,NULL,'2016-08-20 10:01:11','1',NULL,NULL),(15,'Awesome category',NULL,NULL,NULL,NULL,NULL,NULL,'2016-09-05 13:47:00','1',NULL,NULL);

/*Table structure for table `tbl_category_vendor` */

DROP TABLE IF EXISTS `tbl_category_vendor`;

CREATE TABLE `tbl_category_vendor` (
  `vendor_id` bigint(20) unsigned NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`vendor_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_category_vendor` */

/*Table structure for table `tbl_city` */

DROP TABLE IF EXISTS `tbl_city`;

CREATE TABLE `tbl_city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(200) DEFAULT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_city` */

insert  into `tbl_city`(`id`,`city_name`,`is_active`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (4,'hh','active','2016-07-11 15:48:29',NULL,'2016-07-11 15:48:29',NULL),(5,'yhh','active','2016-07-11 15:48:39',NULL,'2016-07-11 15:48:54',14);

/*Table structure for table `tbl_company` */

DROP TABLE IF EXISTS `tbl_company`;

CREATE TABLE `tbl_company` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` text,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_company` */

insert  into `tbl_company`(`id`,`name`,`description`,`is_active`,`created_at`,`updated_at`,`created_by`,`updated_by`) values (1,'Bit Makers Ltd.','Bit Makers Ltd.','active','2016-07-14 09:13:44','2016-07-14 11:41:32','1','14'),(3,'bitmakers2','bitmakers2','active','2016-07-14 11:41:20','2016-07-14 11:41:20','14',NULL),(4,'BITS','BITS','active','2016-07-14 11:41:44','2016-07-14 11:41:44','14',NULL);

/*Table structure for table `tbl_district` */

DROP TABLE IF EXISTS `tbl_district`;

CREATE TABLE `tbl_district` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `d_name` varchar(100) DEFAULT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_district` */

insert  into `tbl_district`(`id`,`d_name`,`is_active`) values (1,'Dhaka','active'),(2,'Chittagong','active'),(3,'Khulna','active'),(4,'Sylhet','active');

/*Table structure for table `tbl_group` */

DROP TABLE IF EXISTS `tbl_group`;

CREATE TABLE `tbl_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` text,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `parent` int(10) DEFAULT '0',
  `c_order` int(10) NOT NULL DEFAULT '0',
  `default_image` varchar(255) NOT NULL,
  `info_format` text,
  `is_budget` enum('active','inactive') DEFAULT 'inactive',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_group` */

insert  into `tbl_group`(`id`,`name`,`description`,`is_active`,`parent`,`c_order`,`default_image`,`info_format`,`is_budget`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (39,'Invitation Card','inviatation card',NULL,0,1,'http://localhost/biyebariadmin/local/storage/app/group/1468230937-39.jpg',NULL,'inactive','2016-07-11 15:55:37',NULL,'2016-07-11 15:59:14',NULL),(40,'Beauty Parlor','beayty parlor','active',0,2,'http://localhost/biyebariadmin/local/storage/app/group/1468230963-40.jpg',NULL,'inactive','2016-07-11 15:56:03',NULL,'2016-07-11 15:59:14',NULL),(41,'Venue & Photographpy','Venue & Photographpy','active',0,3,'http://localhost/biyebariadmin/local/storage/app/group/1468231061-41.jpg',NULL,'inactive','2016-07-11 15:57:41',NULL,'2016-07-11 15:59:14',NULL),(42,'Food& Catering','Food& Catering','active',0,4,'http://localhost/biyebariadmin/local/storage/app/group/1468231088-42.jpg',NULL,'inactive','2016-07-11 15:58:07',NULL,'2016-07-11 15:59:14',NULL),(43,'Intertainment','Intertainment','active',0,5,'http://localhost/biyebariadmin/local/storage/app/group/1468231118-43.jpg',NULL,'inactive','2016-07-11 15:58:38',NULL,'2016-07-11 15:59:14',NULL),(44,'Awesome group',NULL,'active',NULL,1,'default image updated',NULL,NULL,'2016-08-14 14:09:42','1','2016-08-14 14:16:13','1'),(45,'Awesome group',NULL,NULL,NULL,1,'default image',NULL,NULL,'2016-09-05 13:47:31','1',NULL,NULL);

/*Table structure for table `tbl_item` */

DROP TABLE IF EXISTS `tbl_item`;

CREATE TABLE `tbl_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` text,
  `category_id` bigint(20) DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  `unit_id` int(10) DEFAULT NULL,
  `stock` int(10) DEFAULT '0',
  `is_active` enum('active','inactive','deleted') DEFAULT 'inactive',
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_item` */

/*Table structure for table `tbl_item_images` */

DROP TABLE IF EXISTS `tbl_item_images`;

CREATE TABLE `tbl_item_images` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL,
  `image_file_name` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_item_images` */

/*Table structure for table `tbl_location` */

DROP TABLE IF EXISTS `tbl_location`;

CREATE TABLE `tbl_location` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `is_active` enum('active','inactive') DEFAULT 'inactive',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_location` */

insert  into `tbl_location`(`id`,`city_id`,`name`,`is_active`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (6,4,'new market','active','2016-07-11 15:49:06',NULL,'2016-07-11 15:49:06',NULL),(7,5,'new market','active','2016-07-11 15:49:14',NULL,'2016-07-11 15:49:14',NULL);

/*Table structure for table `tbl_role` */

DROP TABLE IF EXISTS `tbl_role`;

CREATE TABLE `tbl_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `permission` text NOT NULL,
  `admin` enum('yes','no') NOT NULL DEFAULT 'yes',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_role` */

/*Table structure for table `tbl_unit` */

DROP TABLE IF EXISTS `tbl_unit`;

CREATE TABLE `tbl_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `is_active` enum('active','inactive') DEFAULT 'active',
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_unit` */

insert  into `tbl_unit`(`id`,`name`,`is_active`,`created_at`,`created_by`,`updated_at`,`updated_by`) values (1,'KG','active','2016-08-14 15:26:25',1,NULL,NULL),(2,'KG','active','2016-08-14 15:26:30',1,NULL,NULL),(3,'KG','active','2016-08-14 15:26:31',1,NULL,NULL),(4,'KG2','active','2016-08-14 15:26:32',1,'2016-08-14 15:28:35',1),(5,'KG',NULL,'2016-09-05 13:48:23',1,NULL,NULL);

/*Table structure for table `tbl_user` */

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `user_role` int(10) DEFAULT NULL,
  `full_name` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `first_name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `last_name` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `contact_no` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `nid_or_passport_no` varchar(50) CHARACTER SET latin1 NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `remember_token` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `verified` enum('yes','no') CHARACTER SET latin1 DEFAULT 'no',
  `company_id` varchar(50) CHARACTER SET latin1 DEFAULT '0',
  `about` text CHARACTER SET latin1,
  `profile_pic` text CHARACTER SET latin1,
  `last_login_ip` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `signup_ip_address` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `position` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `location` text CHARACTER SET latin1 NOT NULL,
  `website` text CHARACTER SET latin1 NOT NULL,
  `fb_url` text CHARACTER SET latin1 NOT NULL,
  `google_url` text CHARACTER SET latin1 NOT NULL,
  `twitter_url` text CHARACTER SET latin1 NOT NULL,
  `linkedin_url` text CHARACTER SET latin1 NOT NULL,
  `verification_code` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_user` */

/*Table structure for table `tbl_vendor` */

DROP TABLE IF EXISTS `tbl_vendor`;

CREATE TABLE `tbl_vendor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `email` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `role_id` int(10) NOT NULL,
  `contact_no` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `remember_token` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `verified` enum('yes','no','cancelled') CHARACTER SET latin1 DEFAULT 'no',
  `company_id` varchar(50) CHARACTER SET latin1 DEFAULT '0',
  `about` text CHARACTER SET latin1,
  `company_logo` text CHARACTER SET latin1,
  `last_login_ip` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `signup_ip_address` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `position` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `location` text CHARACTER SET latin1 NOT NULL,
  `website` text CHARACTER SET latin1,
  `fb_url` text CHARACTER SET latin1,
  `google_url` text CHARACTER SET latin1,
  `twitter_url` text CHARACTER SET latin1,
  `linkedin_url` text CHARACTER SET latin1,
  `verification_code` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbl_vendor` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

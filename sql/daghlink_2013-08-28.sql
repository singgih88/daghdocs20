# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.10)
# Database: daghlink
# Generation Time: 2013-08-28 23:56:44 +0200
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table contact
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `company` text COLLATE utf8_unicode_ci,
  `mail2` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mail` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mail3` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_user_id` (`user_id`),
  CONSTRAINT `contact_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='delete cofrom ';

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;

INSERT INTO `contact` (`id`, `name`, `surname`, `user_id`, `company`, `mail2`, `mail`, `mail3`)
VALUES
	(1,'admin','',9,NULL,NULL,'dagheisha@gmail.com',NULL),
	(2,'ss','ss',9,NULL,NULL,'dagh@dagheisha.com',NULL),
	(3,'test','',9,NULL,NULL,'daghdagh@dagheisha.com',NULL),
	(4,NULL,NULL,9,NULL,NULL,'matera.andrea@gmail.com',NULL),
	(5,NULL,NULL,9,NULL,NULL,'test@test.com',NULL),
	(6,'test','Matera12b2',20,NULL,NULL,'test@test.com',NULL);

/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table file
# ------------------------------------------------------------

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `path` text COLLATE utf8_unicode_ci,
  `filename` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `father` int(10) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `size` int(10) NOT NULL DEFAULT '0',
  KEY `id` (`id`),
  KEY `fk` (`user_id`),
  CONSTRAINT `fk_item` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;

INSERT INTO `file` (`id`, `user_id`, `title`, `path`, `filename`, `description`, `type`, `father`, `date`, `uuid`, `size`)
VALUES
	(481,20,NULL,'/20/file/1b330daf-8ccd-4621-95b4-0b9ed9b52e77.JPG','IMG_0219.JPG','',NULL,20,'2013-08-27 00:00:00','1b330daf-8ccd-4621-95b4-0b9ed9b52e77',82087),
	(483,20,NULL,'/20/file/80f64f72-a722-4224-a749-3a2ac0ec4aa0.jpg','FNV_Wallpaper_12_1920x1200.jpg','',NULL,20,'2013-08-27 00:00:00','80f64f72-a722-4224-a749-3a2ac0ec4aa0',1201995),
	(484,9,NULL,'/9/file/5df5fde6-6d95-4032-a419-578801225e3f.jpg','FNV_Wallpaper_12_1920x1200.jpg','',NULL,19,'2013-08-27 00:00:00','5df5fde6-6d95-4032-a419-578801225e3f',1201995),
	(485,20,NULL,'/20/file/324ab0c3-7171-40ec-8f86-0ffe16490c63.png','Apple_Mac_Icons_Wallpaper_by_Advent_Media.png','',NULL,20,'2013-08-28 00:00:00','324ab0c3-7171-40ec-8f86-0ffe16490c63',2259073),
	(486,20,NULL,'/20/file/9c68c6b4-c84c-4c53-ba3d-458dd9722795.jpeg','Attestato rischio.jpeg','',NULL,20,'2013-08-28 00:00:00','9c68c6b4-c84c-4c53-ba3d-458dd9722795',834041),
	(487,20,NULL,'/20/file/9ece6943-983f-4411-8687-81edb6ad1250.jpeg','Bolletta enel 1.jpeg','',NULL,20,'2013-08-28 00:00:00','9ece6943-983f-4411-8687-81edb6ad1250',677572),
	(488,20,NULL,'/20/file/18756955-ef72-4539-9003-71c53ebe048e.jpeg','Bolletta enel.jpeg','',NULL,20,'2013-08-28 00:00:00','18756955-ef72-4539-9003-71c53ebe048e',626459),
	(489,20,NULL,'/20/file/27b663da-d011-4206-920c-8b40da3e1751.jpg','Firefox_Wallpaper_by_Envirotechture.jpg','',NULL,20,'2013-08-28 00:00:00','27b663da-d011-4206-920c-8b40da3e1751',915283),
	(490,20,NULL,'/20/file/a9658e8f-574f-4e99-a7d7-55ae5a5f29bb.jpg','firefox-wallpaper-1600x1200-0023.jpg','',NULL,20,'2013-08-28 00:00:00','a9658e8f-574f-4e99-a7d7-55ae5a5f29bb',70399),
	(491,20,NULL,'/20/file/75aa6651-45fe-4896-bc78-e8dcafd76127.jpg','FNV_Wallpaper_11_1920x1200.jpg','',NULL,20,'2013-08-28 00:00:00','75aa6651-45fe-4896-bc78-e8dcafd76127',1520531),
	(492,20,NULL,'/20/file/df2bcc2e-7ac6-4d44-aa6e-6b2bd57b8a6e.jpg','FNV_Wallpaper_12_1920x1200.jpg','',NULL,20,'2013-08-28 00:00:00','df2bcc2e-7ac6-4d44-aa6e-6b2bd57b8a6e',1201995),
	(493,20,NULL,'/20/file/c2d45689-20cd-4e6a-8a14-9ece71cdca39.jpeg','Bolletta enel 1.jpeg','',NULL,20,'2013-08-28 00:00:00','c2d45689-20cd-4e6a-8a14-9ece71cdca39',677572),
	(494,20,NULL,'/20/file/a8f06299-b977-425b-81cd-e14e2184fb07.jpeg','Bolletta enel.jpeg','',NULL,20,'2013-08-28 00:00:00','a8f06299-b977-425b-81cd-e14e2184fb07',626459),
	(495,20,NULL,'/20/file/ea194f04-edc7-41d1-a3df-66597d44e7ae.jpg','Firefox_Wallpaper_by_Envirotechture.jpg','',NULL,20,'2013-08-28 00:00:00','ea194f04-edc7-41d1-a3df-66597d44e7ae',915283),
	(496,20,NULL,'/20/file/c19cf4c0-60bb-44ad-b563-59b9a0953450.jpg','firefox-wallpaper-1600x1200-0023.jpg','',NULL,20,'2013-08-28 00:00:00','c19cf4c0-60bb-44ad-b563-59b9a0953450',70399),
	(497,20,NULL,'/20/file/5f052a03-558c-44b2-875a-32dbcb641c20.jpg','FNV_Wallpaper_11_1920x1200.jpg','',NULL,20,'2013-08-28 00:00:00','5f052a03-558c-44b2-875a-32dbcb641c20',1520531),
	(498,20,NULL,'/20/file/412616a7-cec2-4056-91e9-ddcce1264574.jpg','FNV_Wallpaper_12_1920x1200.jpg','',NULL,20,'2013-08-28 00:00:00','412616a7-cec2-4056-91e9-ddcce1264574',1201995),
	(499,20,NULL,'/20/file/a7ec3b76-a402-4cd1-ac96-b07813823e83.jpg','Firefox_Wallpaper_by_Envirotechture.jpg','',NULL,0,'2013-08-28 00:00:00','a7ec3b76-a402-4cd1-ac96-b07813823e83',915283),
	(500,20,NULL,'/20/file/7dd9b70a-dd5c-41de-a491-be2e2dd09e41.jpg','firefox-wallpaper-1600x1200-0023.jpg','',NULL,0,'2013-08-28 00:00:00','7dd9b70a-dd5c-41de-a491-be2e2dd09e41',70399),
	(501,20,NULL,'/20/file/125eb304-6dd6-426d-b8d6-1c3b4753e0b7.jpg','FNV_Wallpaper_11_1920x1200.jpg','',NULL,0,'2013-08-28 00:00:00','125eb304-6dd6-426d-b8d6-1c3b4753e0b7',1520531),
	(502,20,NULL,'/20/file/9125953b-880d-4586-a500-5cd2e3408ae2.jpg','FNV_Wallpaper_12_1920x1200.jpg','',NULL,0,'2013-08-28 00:00:00','9125953b-880d-4586-a500-5cd2e3408ae2',1201995);

/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table file_download
# ------------------------------------------------------------

DROP TABLE IF EXISTS `file_download`;

CREATE TABLE `file_download` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_id` int(11) DEFAULT NULL,
  `ip` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_download_file` (`file_id`),
  CONSTRAINT `fk_file_download_file` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table file_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `file_group`;

CREATE TABLE `file_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL,
  `share` varchar(50) COLLATE utf8_unicode_ci DEFAULT '',
  KEY `id` (`id`),
  KEY `fk_group` (`user_id`),
  CONSTRAINT `fk_group` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `file_group` WRITE;
/*!40000 ALTER TABLE `file_group` DISABLE KEYS */;

INSERT INTO `file_group` (`id`, `user_id`, `title`, `description`, `type`, `uuid`, `date`, `share`)
VALUES
	(18,9,'New One','','file','6c12c865-da57-4121-b0e3-b380fea0e1ec','2013-08-26 00:00:00',''),
	(19,9,'second one','','file','3da45cd6-4619-46ad-9919-f225d65e6e09','2013-08-26 00:00:00',''),
	(20,20,'gruppo di test','','file','e78b77d8-601c-48d6-98b3-0e02dab487f5','2013-08-28 00:00:00','');

/*!40000 ALTER TABLE `file_group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `link` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `father` int(10) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  KEY `id` (`id`),
  KEY `fk` (`user_id`),
  CONSTRAINT `fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;

INSERT INTO `link` (`id`, `user_id`, `title`, `link`, `description`, `type`, `father`, `date`, `uuid`)
VALUES
	(93,9,'gruppo di test','http://localhost:8080/daghlink/link?create','','',94,'2013-02-23 00:00:00','f0e730a5-2810-429f-acf8-62b23d8f50e4'),
	(96,9,'Twitter','http://www.dagheisha.com/prod/main/home.jsp','','',0,'2013-03-08 00:00:00','657972be-23d7-406c-8c09-590875181242'),
	(97,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','027c36c5-80cb-4712-8288-40f82f24cb8f'),
	(98,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(99,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(100,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(101,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(102,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(103,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(104,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(105,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(106,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(107,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(108,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',17,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(109,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',17,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(110,9,'1111 Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',17,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(111,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(112,9,'Twitter','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(113,9,'','http://twitter.github.com/bootstrap/getting-started.html','','',0,'2013-03-08 00:00:00','8691c62e-2382-401c-9005-f5d08ef1890d'),
	(137,9,'Link','http://localhost:8080/daghlink/secure/file','Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ','',0,'2013-08-22 00:00:00','3296881c-05f9-46f7-bf08-2ebdd68fda5b');

/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table link_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `link_group`;

CREATE TABLE `link_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL,
  `share` varchar(50) COLLATE utf8_unicode_ci DEFAULT '',
  KEY `id` (`id`),
  KEY `fk_link_group` (`user_id`),
  CONSTRAINT `fk_link_group` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `link_group` WRITE;
/*!40000 ALTER TABLE `link_group` DISABLE KEYS */;

INSERT INTO `link_group` (`id`, `user_id`, `title`, `description`, `type`, `uuid`, `date`, `share`)
VALUES
	(16,9,'Twitterjj','test','link','5609081b-fb7b-410c-9f6d-c33509814c4a','2013-03-26 00:00:00',NULL),
	(17,9,'devel','devel','link','6cdc8081-89b8-4ea9-93c6-7268ccbec272','2013-08-26 00:00:00',NULL);

/*!40000 ALTER TABLE `link_group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table persistent_logins
# ------------------------------------------------------------

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `series` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `token` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_used` datetime DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;

INSERT INTO `persistent_logins` (`username`, `series`, `token`, `last_used`)
VALUES
	('admin','DVOVb3ftP7PGbd61ckWwzg==','KSP7uGk/bv3mmoCvFwYt0A==','2013-08-28 23:36:44');

/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table property
# ------------------------------------------------------------

DROP TABLE IF EXISTS `property`;

CREATE TABLE `property` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `key` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;

INSERT INTO `property` (`id`, `user_id`, `key`, `value`)
VALUES
	(571,NULL,'general.basepath.notAvailable','//notAvailable.jpg'),
	(572,NULL,'general.basepath.avatar','//avatar.jpg'),
	(573,NULL,'general.basepath','//uploads'),
	(574,NULL,'general.mail.user','devel@daghlink.com'),
	(575,NULL,'general.mail.host','smtp.daghlink.com'),
	(576,NULL,'general.mail.port','587'),
	(577,NULL,'general.mail.password','password'),
	(578,NULL,'general.upload.size.limit','1000000'),
	(579,NULL,'general.mail.from','devel@daghlink.com'),
	(580,NULL,'general.max.quota','50000'),
	(581,NULL,'general.sql.lock','locked'),
	(582,NULL,'general.page.size','10');

/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table queue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `queue`;

CREATE TABLE `queue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_job` (`user_id`),
  CONSTRAINT `user_job` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `mail` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `avatarPath` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `username` (`username`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `surname`, `username`, `password`, `enabled`, `mail`, `avatarPath`)
VALUES
	(9,'Andrea123','Matera12b2','admin','admin',1,'dagheisha@gmail.com','/9/resource/Firefox_Wallpaper_by_Envirotechture.jpg'),
	(20,'test','','test','test',1,'test@test.com','/20/resource/full_hd_119.jpg');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_authority
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_authority`;

CREATE TABLE `user_authority` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `authority` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `fk1` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;

INSERT INTO `user_authority` (`id`, `username`, `authority`)
VALUES
	(69,'admin','ROLE_ADMIN'),
	(70,'admin','ROLE_USER'),
	(71,'test','ROLE_USER');

/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

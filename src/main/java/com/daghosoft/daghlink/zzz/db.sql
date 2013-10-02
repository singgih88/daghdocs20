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


# Dump of table queue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `queue`;

CREATE TABLE `queue` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`fail` INT(10) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `user_job` (`user_id`),
	CONSTRAINT `user_job` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;



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
  `quota` int(11) NOT NULL DEFAULT '0',
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `username` (`username`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `surname`, `username`, `password`, `enabled`, `mail`, `avatarPath`,`quota`)
VALUES
	(1,'admin','admin','admin','admin',1,'devel@devel.com','',500000);

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
	(70,'admin','ROLE_USER');
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

INSERT INTO `property` ( `user_id`, `key`, `value`) VALUES
	( NULL, 'general.basepath.notAvailable', '//notAvailable.jpg'),
	( NULL, 'general.basepath.avatar', '//avatar.jpg'),
	( NULL, 'general.basepath', 'c://uploads'),
	( NULL, 'general.mail.user', 'devel@daghlink.com'),
	( NULL, 'general.mail.host', 'smtp.daghlink.com'),
	( NULL, 'general.mail.port', '587'),
	( NULL, 'general.mail.password', 'password'),
	( NULL, 'general.upload.size.limit', '1000000'),
	( NULL, 'general.upload.size.limit.avatar', '1000000'),
	( NULL, 'general.mail.from', 'Daghlink<devel@daghlink.com>'),
	( NULL, 'general.mail.ccn.admin', 'false'),
	( NULL, 'general.mail.fail.limit', '3'),
	( NULL, 'general.max.quota', '50000'),
	( NULL, 'general.sql.lock', 'locked'),
	( NULL, 'general.mail.ccn.admin', 'false'),
	( NULL, 'general.page.size', '10');
	

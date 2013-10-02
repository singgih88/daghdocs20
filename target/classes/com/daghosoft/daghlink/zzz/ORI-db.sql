
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
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;

/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `file_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL,
  `share` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  KEY `id` (`id`),
  KEY `fk_group` (`user_id`),
  CONSTRAINT `fk_group` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `link_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `title` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uuid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` timestamp NULL DEFAULT NULL,
  `share` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  KEY `id` (`id`),
  KEY `fk_link_group` (`user_id`),
  CONSTRAINT `fk_link_group` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `file` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`user_id` INT(10) NULL DEFAULT NULL,
	`title` VARCHAR(150) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`path` TEXT NULL DEFAULT NULL,
	`filename` VARCHAR(250) NOT NULL COLLATE 'utf8_unicode_ci',
	`description` TEXT NULL COLLATE 'utf8_unicode_ci',
	`type` VARCHAR(4) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`father` INT(10) NULL DEFAULT NULL,
	`date` TIMESTAMP NULL DEFAULT NULL,
	`uuid` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8_unicode_ci',
	`size` INT(10) NOT NULL DEFAULT '0',
	INDEX `id` (`id`),
	INDEX `fk` (`user_id`),
	CONSTRAINT `fk_item` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


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

/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`surname` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`username` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`password` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`enabled` TINYINT(1) NOT NULL DEFAULT '1',
	`mail` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`avatarPath` VARCHAR(500) NULL,
	UNIQUE INDEX `mail` (`mail`),
	UNIQUE INDEX `username` (`username`),
	INDEX `id` (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;



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

/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `property` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `key` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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

CREATE TABLE `queue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_job` (`user_id`),
  CONSTRAINT `user_job` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `file_download` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_id` int(11) DEFAULT NULL,
  `ip` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_download_file` (`file_id`),
  CONSTRAINT `fk_file_download_file` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `user` (`username`, `password`, `mail`) VALUES ('admin', 'admin', 'admin@local.priv');
INSERT INTO `user_authority` (`username`, `authority`) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO `user_authority` (`username`, `authority`) VALUES ('admin', 'ROLE_USER');


INSERT INTO `property` ( `user_id`, `key`, `value`) VALUES
	( NULL, 'general.basepath.notAvailable', '//notAvailable.jpg'),
	( NULL, 'general.basepath.avatar', '//avatar.jpg'),
	( NULL, 'general.basepath', 'c://uploads'),
	( NULL, 'general.mail.user', 'devel@daghlink.com'),
	( NULL, 'general.mail.host', 'smtp.daghlink.com'),
	( NULL, 'general.mail.port', '587'),
	( NULL, 'general.mail.password', 'password'),
	( NULL, 'general.upload.size.limit', '1000000'),
	( NULL, 'general.mail.from', 'devel@daghlink.com'),
	( NULL, 'general.max.quota', '50000'),
	( NULL, 'general.sql.lock', 'locked'),
	( NULL, 'general.page.size', '10');


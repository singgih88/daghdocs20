#ALTER TABLE `user` ADD `quota` INT  NOT NULL  DEFAULT '1'  AFTER `avatarPath`;
ALTER TABLE `queue` ADD COLUMN `fail` INT(10) NOT NULL DEFAULT '0' AFTER `user_id`;

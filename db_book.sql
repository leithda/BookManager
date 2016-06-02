/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : db_book

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-05-27 07:27:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_book`
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bookName` varchar(20) NOT NULL,
  `author` varchar(20) NOT NULL,
  `price` float(10,1) DEFAULT NULL,
  `bookDesc` varchar(1000) DEFAULT NULL,
  `bookTypeId` int(11) unsigned DEFAULT NULL,
  `state` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT '0为未借走 1被借中',
  PRIMARY KEY (`id`),
  KEY `typeId` (`bookTypeId`),
  CONSTRAINT `typeId` FOREIGN KEY (`bookTypeId`) REFERENCES `t_booktype` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('1', 'Java编程思想', '黎士达', '999.0', '这是我的第一本书', '5', '0000000001');
INSERT INTO `t_book` VALUES ('4', '环境科学', '刘梦迪', '25.0', '刘梦迪的第一本书，很受欢迎', '6', '0000000001');
INSERT INTO `t_book` VALUES ('8', 'PHP起航', '黎士达', '99.0', '黎士达的第二本书', '5', '0000000001');
INSERT INTO `t_book` VALUES ('9', '樊西江的一生', '黎士达', '12.5', '樊西江逗比的一辈子。', '7', '0000000000');

-- ----------------------------
-- Table structure for `t_booktype`
-- ----------------------------
DROP TABLE IF EXISTS `t_booktype`;
CREATE TABLE `t_booktype` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `bookTypeName` varchar(20) NOT NULL,
  `bookTypeDesc` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_booktype
-- ----------------------------
INSERT INTO `t_booktype` VALUES ('5', '计算机类', '计算机类图书，是我本专业书籍');
INSERT INTO `t_booktype` VALUES ('6', '环保类', '环保类图书');
INSERT INTO `t_booktype` VALUES ('7', '政治类', '政治类图书');
INSERT INTO `t_booktype` VALUES ('8', '数学类', '数学类图书');
INSERT INTO `t_booktype` VALUES ('9', '语文类', '语文类图书');

-- ----------------------------
-- Table structure for `t_lendbook`
-- ----------------------------
DROP TABLE IF EXISTS `t_lendbook`;
CREATE TABLE `t_lendbook` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `bookid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `bid` (`bookid`),
  KEY `uid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lendbook
-- ----------------------------
INSERT INTO `t_lendbook` VALUES ('18', '2', '1');
INSERT INTO `t_lendbook` VALUES ('19', '2', '4');
INSERT INTO `t_lendbook` VALUES ('20', '3', '8');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL COMMENT '帐号',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `role` int(2) DEFAULT '0',
  `pname` varchar(40) CHARACTER SET gbk NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '123456', '1', '管理员');
INSERT INTO `t_user` VALUES ('2', 'user', '123456', '0', '黎士达');
INSERT INTO `t_user` VALUES ('3', 'user2', '123456', '0', '刘梦迪');

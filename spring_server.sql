/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : spring_server

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2022-06-09 21:24:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `data`
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `deviceKey` varchar(30) DEFAULT NULL,
  `temperature` float(10,0) DEFAULT NULL,
  `humidity` float(10,0) DEFAULT NULL,
  `deviceData` float(10,0) DEFAULT NULL,
  `createtime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`,`createtime`)
) ENGINE=InnoDB AUTO_INCREMENT=3537 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of data
-- ----------------------------
INSERT INTO `data` VALUES ('1', 'C8:C9:A3:64:AA:1A', '0', '0', '0', '2022-06-09 12:35:17');
INSERT INTO `data` VALUES ('1550', 'C1', '20', '60', '15', '2022-06-08 18:36:02');
INSERT INTO `data` VALUES ('1552', 'A1', '23', '45', '11', '2022-06-08 18:55:52');

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `productKey` varchar(30) DEFAULT NULL,
  `deviceName` varchar(20) DEFAULT NULL,
  `ledStatus` varchar(5) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', 'C8:C9:A3:64:AA:1A', 'esp8266', 'on', '2022-06-07 01:16:40', '0');
INSERT INTO `device` VALUES ('2', 'C1', '监测2', 'off', '2022-06-07 12:36:12', null);
INSERT INTO `device` VALUES ('3', 'A1', '噪音监测', 'on', '2022-06-02 12:36:17', null);

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `deviceName` varchar(20) DEFAULT NULL,
  `message` text,
  `checked` int(2) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('98', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:17:59');
INSERT INTO `message` VALUES ('99', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:17:59');
INSERT INTO `message` VALUES ('100', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:17:59');
INSERT INTO `message` VALUES ('101', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:00');
INSERT INTO `message` VALUES ('102', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:00');
INSERT INTO `message` VALUES ('103', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:00');
INSERT INTO `message` VALUES ('104', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:01');
INSERT INTO `message` VALUES ('105', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:01');
INSERT INTO `message` VALUES ('106', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:02');
INSERT INTO `message` VALUES ('107', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 15:18:02');
INSERT INTO `message` VALUES ('108', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:48:50');
INSERT INTO `message` VALUES ('109', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:31');
INSERT INTO `message` VALUES ('110', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:31');
INSERT INTO `message` VALUES ('111', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:31');
INSERT INTO `message` VALUES ('112', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:32');
INSERT INTO `message` VALUES ('113', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:32');
INSERT INTO `message` VALUES ('114', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:33');
INSERT INTO `message` VALUES ('115', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:34');
INSERT INTO `message` VALUES ('116', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:34');
INSERT INTO `message` VALUES ('117', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:34');
INSERT INTO `message` VALUES ('118', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:34');
INSERT INTO `message` VALUES ('119', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:34');
INSERT INTO `message` VALUES ('120', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:35');
INSERT INTO `message` VALUES ('121', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:35');
INSERT INTO `message` VALUES ('122', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:44');
INSERT INTO `message` VALUES ('123', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:49:59');
INSERT INTO `message` VALUES ('124', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:51:44');
INSERT INTO `message` VALUES ('125', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:52:57');
INSERT INTO `message` VALUES ('126', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:53:16');
INSERT INTO `message` VALUES ('127', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:54:20');
INSERT INTO `message` VALUES ('128', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:54:24');
INSERT INTO `message` VALUES ('129', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:54:26');
INSERT INTO `message` VALUES ('130', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:54:32');
INSERT INTO `message` VALUES ('131', 'asd1234', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:56:01');
INSERT INTO `message` VALUES ('132', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:57:05');
INSERT INTO `message` VALUES ('133', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:57:09');
INSERT INTO `message` VALUES ('134', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:02');
INSERT INTO `message` VALUES ('135', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:49');
INSERT INTO `message` VALUES ('136', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:49');
INSERT INTO `message` VALUES ('137', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:49');
INSERT INTO `message` VALUES ('138', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:50');
INSERT INTO `message` VALUES ('139', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:51');
INSERT INTO `message` VALUES ('140', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:58:53');
INSERT INTO `message` VALUES ('141', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:00');
INSERT INTO `message` VALUES ('142', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:01');
INSERT INTO `message` VALUES ('143', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:01');
INSERT INTO `message` VALUES ('144', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:01');
INSERT INTO `message` VALUES ('145', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:03');
INSERT INTO `message` VALUES ('146', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:03');
INSERT INTO `message` VALUES ('147', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:03');
INSERT INTO `message` VALUES ('148', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:04');
INSERT INTO `message` VALUES ('149', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:04');
INSERT INTO `message` VALUES ('150', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:09');
INSERT INTO `message` VALUES ('151', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:09');
INSERT INTO `message` VALUES ('152', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:09');
INSERT INTO `message` VALUES ('153', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:10');
INSERT INTO `message` VALUES ('154', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:10');
INSERT INTO `message` VALUES ('155', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:10');
INSERT INTO `message` VALUES ('156', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:13');
INSERT INTO `message` VALUES ('157', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:13');
INSERT INTO `message` VALUES ('158', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:13');
INSERT INTO `message` VALUES ('159', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:34');
INSERT INTO `message` VALUES ('160', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:35');
INSERT INTO `message` VALUES ('161', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:35');
INSERT INTO `message` VALUES ('162', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:35');
INSERT INTO `message` VALUES ('163', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:35');
INSERT INTO `message` VALUES ('164', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:36');
INSERT INTO `message` VALUES ('165', 'asd123456', 'Warning! 设备检测到异常！', '0', '2022-06-09 20:59:36');
INSERT INTO `message` VALUES ('166', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:11');
INSERT INTO `message` VALUES ('167', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:11');
INSERT INTO `message` VALUES ('168', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:11');
INSERT INTO `message` VALUES ('169', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:11');
INSERT INTO `message` VALUES ('170', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:12');
INSERT INTO `message` VALUES ('171', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:12');
INSERT INTO `message` VALUES ('172', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:16');
INSERT INTO `message` VALUES ('173', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:16');
INSERT INTO `message` VALUES ('174', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:16');
INSERT INTO `message` VALUES ('175', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:16');
INSERT INTO `message` VALUES ('176', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:19');
INSERT INTO `message` VALUES ('177', 'esp8266', 'Warning! 设备检测到异常！', '0', '2022-06-09 21:00:27');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Uid` varchar(50) DEFAULT NULL,
  `phone` int(15) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('9', 'd6f46a40a77b4ca3a7019c71311b084e', '123', '123');
INSERT INTO `user` VALUES ('10', '7a3267a57c904aab99cbf330fa56a54f', '1573333333', '123456');

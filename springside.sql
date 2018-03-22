/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50156
Source Host           : localhost:3306
Source Database       : springside

Target Server Type    : MYSQL
Target Server Version : 50156
File Encoding         : 65001

Date: 2016-02-29 18:08:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `toid` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `major` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`toid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '1304020313', '青龙', '23', '男', '计算机');
INSERT INTO `student` VALUES ('2', '1304020301', '白虎', '24', '男', '计算机');
INSERT INTO `student` VALUES ('3', '1304020302', '朱雀', '21', '女', '计算机');
INSERT INTO `student` VALUES ('4', '1304020303', '玄武', '22', '男', '计算机');
INSERT INTO `student` VALUES ('5', '1304020305', '饕餮', '22', '男', '计算机');
INSERT INTO `student` VALUES ('6', '1304020306', '秦广王', '23', '男', '计算机');
INSERT INTO `student` VALUES ('7', '1304020307', '楚江王', '22', '女', '计算机');
INSERT INTO `student` VALUES ('8', '1304020308', '阎罗王', '22', '男', '计算机');
INSERT INTO `student` VALUES ('9', '1304020309', '宋帝王', '24', '男', '计算机');
INSERT INTO `student` VALUES ('10', '1304020310', '平等王', '22', '男', '计算机');
INSERT INTO `student` VALUES ('11', '130402011', '卞城王', '22', '男', '计算机');
INSERT INTO `student` VALUES ('12', '13040212', '泰山王', '21', '女', '计算机');
INSERT INTO `student` VALUES ('13', '10086', '中国移动', '25', '男', '互联网');

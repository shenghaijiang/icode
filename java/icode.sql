/*
 Navicat Premium Data Transfer

 Source Server         : 139.196.125.197
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 139.196.125.197:3306
 Source Schema         : git-icode

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 14/02/2019 14:20:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for connectioninfo
-- ----------------------------
DROP TABLE IF EXISTS `connectioninfo`;
CREATE TABLE `connectioninfo`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ConnectionInfo' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PackageName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SqlType` int(11) NULL DEFAULT NULL COMMENT '1:mysql 2:sqlServer',
  `DbHost` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DbName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DbUser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DbPassword` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MakeBillMan` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CreateDate` datetime(0) NOT NULL COMMENT '创建时间',
  `Modifier` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `ModifyDate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `DeleteFlag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记，0删除',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Project' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, 'icode', '代码色生成Demo', 'cn.xtits.icode', 1, '192.168.2.102:3306', 'icode', 'root', '88888888', NULL, '2017-12-05 10:17:15', 'test', '2018-02-28 13:38:27', b'0');
INSERT INTO `project` VALUES (2, 'ibill', '表单', 'cn.xtits.ibill', 1, '192.168.2.102:3306', 'ibill', 'root', '88888888', NULL, '2017-12-07 15:30:19', 'test', '2018-02-28 13:38:27', b'0');
INSERT INTO `project` VALUES (3, 'ibasic', '表单-炬泰', 'cn.xtits.ibasic', 1, '119.37.194.4:11161', 'ibasic', 'root', '88888888', NULL, '2017-12-07 15:30:19', 'test', '2018-02-28 13:38:27', b'0');
INSERT INTO `project` VALUES (4, 'iformula', '表单', 'cn.xtits.iformula', 1, '192.168.2.102:3306', 'iformula', 'root', '88888888', NULL, '2017-12-07 15:30:19', 'test', '2018-02-28 13:38:27', b'0');
INSERT INTO `project` VALUES (5, 'xtp', 'xtp', 'cn.xtits.xtp', 1, '119.37.194.4:11161', 'xtp', 'root', '88888888', NULL, '2017-12-07 15:30:19', 'test', '2018-02-28 13:38:27', b'0');

-- ----------------------------
-- Table structure for schemata
-- ----------------------------
DROP TABLE IF EXISTS `schemata`;
CREATE TABLE `schemata`  (
  `Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Schemata' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tableinfo
-- ----------------------------
DROP TABLE IF EXISTS `tableinfo`;
CREATE TABLE `tableinfo`  (
  `Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'TableInfo' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

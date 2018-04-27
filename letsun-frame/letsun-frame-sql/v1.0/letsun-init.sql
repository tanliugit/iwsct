/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.8
Source Server Version : 50636
Source Host           : 192.168.1.8:3306
Source Database       : letsun-demo

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-04-12 15:57:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_error_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_log`;
CREATE TABLE `sys_error_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `error_code` varchar(32) NOT NULL DEFAULT '0' COMMENT '错误代码',
  `request_url` varchar(128) NOT NULL COMMENT '请求URL',
  `params` varchar(512) NOT NULL COMMENT '请求参数',
  `message` varchar(512) NOT NULL COMMENT '错误信息',
  `exception` text NOT NULL COMMENT '异常信息',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统错误日志';

-- ----------------------------
-- Records of sys_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `code` varchar(128) NOT NULL COMMENT '功能编码',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型：0通用功能/1专属功能',
  `source` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0管理员增加/1系统初始化',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0启用/1关闭',
  `orderby` int(8) NOT NULL DEFAULT '0' COMMENT '排序',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='系统功能';

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES ('1', '查询', 'search', '0', '1', '0', '5', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('2', '列表', 'list', '0', '1', '0', '4', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('3', '添加', 'add', '0', '1', '0', '1', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('4', '编辑', 'edit', '0', '1', '0', '3', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('5', '详情', 'detail', '0', '1', '0', '6', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('6', '删除', 'delete', '0', '1', '0', '2', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('7', '授权', 'grant', '1', '1', '0', '7', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('8', '导出', 'export', '1', '1', '0', '8', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('9', '导入', 'import', '1', '1', '0', '8', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_function` VALUES ('10', '测试', 'test', '0', '0', '0', '0', '0', 'manager', '2018-03-26 17:05:32', null, null);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父节点ID',
  `function_id` bigint(20) DEFAULT NULL COMMENT '功能ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `url` varchar(128) NOT NULL COMMENT 'URL',
  `icon` varchar(128) DEFAULT NULL COMMENT 'icon图片',
  `isopen` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否展开:0:是 ,1:否',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型：0菜单/1目录/2功能',
  `level` tinyint(1) NOT NULL DEFAULT '1' COMMENT '菜单层级',
  `source` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0管理员增加/1系统初始化',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0启用/1禁用',
  `orderby` int(8) DEFAULT '0' COMMENT '排序',
  `remark` varchar(258) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0', '-1', '0', '顶级菜单', '#', '', '0', '0', '0', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('1', '0', '0', '首页', '/index', '', '0', '0', '1', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('2', '0', '0', '系统管理', '/system/user/index', null, '0', '0', '1', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('3', '2', '0', '用户管理', '/system/user/index', '', '0', '0', '2', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('4', '3', '1', '查询', '/system/user/search', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('5', '3', '2', '列表', '/system/user/list', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('6', '3', '3', '添加', '/system/user/add', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('7', '3', '4', '编辑', '/system/user/edit', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('8', '3', '5', '详情', '/system/user/detail', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('9', '3', '6', '删除', '/system/user/delete', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('10', '3', '7', '授权', '/system/user/grant', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('11', '3', '8', '导出', '/system/user/export', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('12', '3', '9', '导入', '/system/user/import', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('13', '2', '0', '角色管理', '/system/role/index', null, '0', '0', '2', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('14', '13', '1', '查询', '/system/role/search', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('15', '13', '2', '列表', '/system/role/list', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('16', '13', '3', '添加', '/system/role/add', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('17', '13', '4', '编辑', '/system/role/edit', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('18', '13', '5', '详情', '/system/role/detail', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('19', '13', '6', '删除', '/system/role/delete', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('20', '13', '7', '授权', '/system/role/grant', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('21', '13', '8', '导出', '/system/role/export', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('22', '13', '9', '导入', '/system/role/import', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('23', '2', '0', '菜单管理', '/system/menu/index', null, '0', '0', '2', '1', '0', '6', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('24', '23', '1', '查询', '/system/menu/search', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('25', '23', '2', '列表', '/system/menu/list', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('26', '23', '3', '添加', '/system/menu/add', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('27', '23', '4', '编辑', '/system/menu/edit', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('28', '23', '5', '详情', '/system/menu/detail', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('29', '23', '6', '删除', '/system/menu/delete', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('30', '23', '7', '授权', '/system/menu/grant', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('31', '23', '8', '导出', '/system/menu/export', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('32', '23', '9', '导入', '/system/menu/import', null, '0', '2', '3', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('33', '2', '0', '功能管理', '/system/function/index', null, '0', '0', '2', '1', '0', '0', null, '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('34', '2', '0', '数据监控', '/druid/index.html', '', '0', '0', '2', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('35', '2', '0', '操作日志', '/system/operationLog/index', '', '0', '0', '2', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_menu` VALUES ('36', '2', '0', '错误日志', '/system/errorLog/index', '', '0', '0', '2', '1', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_ip` varchar(30) NOT NULL COMMENT '操作人IP',
  `request_url` varchar(128) NOT NULL COMMENT '操作人请求URL',
  `request_params` text NOT NULL COMMENT '请求参数',
  `class_path` varchar(512) NOT NULL COMMENT '类路径',
  `method_name` varchar(32) NOT NULL COMMENT '请求方法名称',
  `method_params` text NOT NULL COMMENT '方法参数',
  `business_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '业务类型：0其它1新增2修改3删除4授权5导出6导入7登录8退出登录9禁止访问',
  `business_name` varchar(128) NOT NULL COMMENT '业务名称：如用户管理',
  `business_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '业务状态：0其它1成功2失败',
  `business_remarks` varchar(512) DEFAULT NULL COMMENT '业务备注',
  `operator_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '操作用户类型：0其它1后台用户2手机端用户',
  `before_update_json` text COMMENT '更新前JSOn对象',
  `after_update_json` text COMMENT '更新后JSOn对象',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) NOT NULL COMMENT '父节点ID',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型：0新增普通角色/1初始化超级角色',
  `source` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0管理员增加/1系统初始化',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0启用/1禁用',
  `orderby` int(8) NOT NULL DEFAULT '0' COMMENT '排序',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '后台管理员', '0', '1', '0', '1', '0', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role` VALUES ('2', '超级管理员', '1', '1', '0', '0', '0', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role` VALUES ('3', '普通管理员', '1', '0', '0', '0', '0', '0', 'manager', '2018-03-26 17:05:32', 'manager', '2018-04-11 11:14:52');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色 | 菜单';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '2', '1', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('2', '2', '2', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('3', '2', '3', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('4', '2', '13', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('5', '2', '23', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('6', '2', '33', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('7', '2', '34', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('8', '2', '35', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_role_menu` VALUES ('9', '2', '36', '0', 'manager', '2018-03-26 17:05:32', null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `image_face` varchar(128) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别:0男，1女',
  `mobile` varchar(16) NOT NULL COMMENT '手机',
  `phone` varchar(32) DEFAULT NULL COMMENT '固话',
  `qq` varchar(32) DEFAULT NULL COMMENT 'QQ号',
  `weixin` varchar(32) DEFAULT NULL COMMENT '微信',
  `alipay` varchar(64) DEFAULT NULL COMMENT '支付宝',
  `address` varchar(512) DEFAULT NULL COMMENT '地址',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型:0平台会员1后台管理员',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态:0启用1锁定/冻结',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', null, 'manager', '超级管理员', '13888888888@163.com', '1', '13888888888', '', '', '', '', '深圳', '1', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_user` VALUES ('2', null, 'admin', '普通管理员', '138888888838@163.com', '1', '13888888888', '', '', '', '', '深圳', '0', '0', '', '0', 'manager', '2018-03-26 17:05:32', null, null);

-- ----------------------------
-- Table structure for sys_user_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login`;
CREATE TABLE `sys_user_login` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `origin_code` varchar(32) NOT NULL COMMENT '登录源编码:数据字典(PLATFORM:平台,QQ,WEIXIN:微信)',
  `account` varchar(32) NOT NULL COMMENT '登录账号',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户登录';

-- ----------------------------
-- Records of sys_user_login
-- ----------------------------
INSERT INTO `sys_user_login` VALUES ('1', '1', 'PLATFORM', 'manager', 'e10adc3949ba59abbe56e057f20f883e', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_user_login` VALUES ('2', '2', 'PLATFORM', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '0', 'manager', '2018-03-26 17:05:32', null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0:否 ,1:是',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户 | 角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '2', '0', 'manager', '2018-03-26 17:05:32', null, null);
INSERT INTO `sys_user_role` VALUES ('3', '2', '3', '0', 'manager', '2018-03-26 17:05:32', null, null);

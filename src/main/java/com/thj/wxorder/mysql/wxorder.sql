/*
 Navicat Premium Data Transfer

 Source Server         : 118.25.103.96
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 118.25.103.96:3306
 Source Schema         : wxorder

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 23/07/2018 12:17:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `product_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名字',
  `product_price` decimal(8, 2) NOT NULL COMMENT '商品价格',
  `product_quantity` int(11) NOT NULL COMMENT '商品数量',
  `product_icon` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('111111', '123123', '01', '皮蛋粥', 3.20, 1, 'http:xxx.jpg', '2018-07-04 11:02:41', '2018-07-04 11:02:41');
INSERT INTO `order_detail` VALUES ('4105921531110839794', '6449111531110839703', '01', '皮蛋粥', 3.20, 1, 'http://xxx.jpg', '2018-07-09 12:34:22', '2018-07-09 12:34:22');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `buyer_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8, 2) NOT NULL COMMENT '订单总价',
  `order_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '订单状态，默认0新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '支付状态，默认0未支付',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_buyer_openid`(`buyer_openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('123123', '汤好杰', '15901749277', '上海', '3333333', 30.50, 0, 0, '2018-07-03 16:53:44', '2018-07-03 16:53:44');
INSERT INTO `order_master` VALUES ('123222', '汤好杰', '15901749277', '上海', '3333333', 30.50, 0, 0, '2018-07-03 17:34:27', '2018-07-03 17:34:27');
INSERT INTO `order_master` VALUES ('6449111531110839703', 'tang', '15923125232', '上海市', '1101110', 3.20, 0, 0, '2018-07-09 12:34:22', '2018-07-09 12:34:22');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `uqe_category_type`(`category_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '类目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (1, '热销榜', 2, '2018-07-03 11:17:17', '2018-07-03 11:17:17');
INSERT INTO `product_category` VALUES (2, '新品榜', 3, '2018-07-03 11:25:24', '2018-07-03 11:25:24');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `product_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8, 2) NOT NULL COMMENT '商品单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小图',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `product_status` int(11) DEFAULT NULL COMMENT '商品状态',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('01', '皮蛋粥', 3.20, 99998, '很好喝的粥', 'http://xxx.jpg', 2, '2018-07-03 11:18:59', '2018-07-09 12:34:22', 0);
INSERT INTO `product_info` VALUES ('02', '虾', 5.00, 999999, '很好吃的虾', 'http://xxx.jpg', 3, '2018-07-03 11:26:04', '2018-07-03 11:26:22', 0);

SET FOREIGN_KEY_CHECKS = 1;

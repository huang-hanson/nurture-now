-- 健身健身数据库表结构

-- 用户信息表
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `wechat_open_id` varchar(100) DEFAULT NULL COMMENT '微信OpenID',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像URL',
  `height` int DEFAULT NULL COMMENT '身高(cm)',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别(male/female)',
  `target_weight` decimal(5,2) DEFAULT NULL COMMENT '目标体重',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0-未删除 1-已删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wechat_open_id` (`wechat_open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 食物库表
CREATE TABLE `foods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '食物ID',
  `name` varchar(200) NOT NULL COMMENT '食物名称',
  `calories` int NOT NULL COMMENT '卡路里(每100g)',
  `category` varchar(50) NOT NULL COMMENT '分类(主食/蔬菜/水果/肉类/零食/饮料)',
  `image_url` varchar(500) DEFAULT NULL COMMENT '图片URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食物库表';

-- 运动库表
CREATE TABLE `exercises` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '运动ID',
  `name` varchar(200) NOT NULL COMMENT '运动名称',
  `calories_burned` int NOT NULL COMMENT '消耗卡路里(每小时)',
  `category` varchar(50) NOT NULL COMMENT '分类(有氧/力量/拉伸/球类/其他)',
  `duration` int DEFAULT NULL COMMENT '建议时长(分钟)',
  `image_url` varchar(500) DEFAULT NULL COMMENT '图片URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动库表';

-- 训练计划表
CREATE TABLE `training_plans` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `title` varchar(200) NOT NULL COMMENT '计划标题',
  `description` text COMMENT '计划描述',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `status` varchar(20) NOT NULL DEFAULT 'active' COMMENT '状态(active/completed/cancelled)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='训练计划表';

-- 训练计划项表
CREATE TABLE `training_plan_items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划项ID',
  `plan_id` bigint NOT NULL COMMENT '计划ID',
  `exercise_id` bigint NOT NULL COMMENT '运动ID',
  `sets` int DEFAULT NULL COMMENT '组数',
  `reps` int DEFAULT NULL COMMENT '次数',
  `duration` int DEFAULT NULL COMMENT '时长(分钟)',
  `order_num` int NOT NULL COMMENT '顺序',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_order` (`order_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='训练计划项表';

-- 健康记录表
CREATE TABLE `health_records` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `calories_consumed` int DEFAULT NULL COMMENT '摄入卡路里',
  `calories_burned` int DEFAULT NULL COMMENT '消耗卡路里',
  `record_date` date NOT NULL COMMENT '记录日期',
  `notes` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 签到记录表
CREATE TABLE `check_in_records` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '签到ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `check_in_date` date NOT NULL COMMENT '签到日期',
  `consecutive_days` int NOT NULL DEFAULT '0' COMMENT '连续签到天数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`,`check_in_date`),
  KEY `idx_check_in_date` (`check_in_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到记录表';

-- 运动量目标表
CREATE TABLE `exercise_goals` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '目标ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `goal_type` varchar(20) NOT NULL COMMENT '目标类型(daily/weekly/monthly)',
  `duration_minutes` int DEFAULT NULL COMMENT '时长目标(分钟)',
  `calories` int DEFAULT NULL COMMENT '卡路里目标',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_type` (`user_id`,`goal_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动量目标表';

-- 插入示例数据
INSERT INTO `foods` (`name`, `calories`, `category`, `description`) VALUES
('苹果', 52, '水果', '富含维生素和纤维'),
('香蕉', 89, '水果', '高钾水果，补充能量'),
('米饭', 116, '主食', '主要能量来源'),
('鸡胸肉', 165, '肉类', '高蛋白低脂肪'),
('鸡蛋', 155, '肉类', '优质蛋白质'),
('牛奶', 54, '饮料', '富含钙质'),
('西红柿', 18, '蔬菜', '富含维生素C'),
('燕麦', 389, '主食', '高纤维谷物'),
('三文鱼', 208, '肉类', '富含Omega-3'),
('巧克力', 546, '零食', '高热量零食');

INSERT INTO `exercises` (`name`, `calories_burned`, `category`, `duration`, `description`) VALUES
('跑步', 300, '有氧', 30, '全身有氧运动'),
('游泳', 400, '有氧', 30, '全身低冲击运动'),
('跳绳', 350, '有氧', 30, '高效燃脂'),
('俯卧撑', 200, '力量', 15, '上肢力量训练'),
('深蹲', 250, '力量', 15, '下肢力量训练'),
('瑜伽', 150, '拉伸', 30, '身心放松'),
('篮球', 350, '球类', 30, '团队运动'),
('羽毛球', 300, '球类', 30, '敏捷性训练'),
('仰卧起坐', 180, '力量', 15, '核心训练'),
('太极', 100, '拉伸', 30, '身心平衡');

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    JOB_NAME          VARCHAR(200) NOT NULL,
    JOB_GROUP         VARCHAR(200) NOT NULL,
    DESCRIPTION       VARCHAR(250) NULL,
    JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
    IS_DURABLE        VARCHAR(1)   NOT NULL,
    IS_NONCONCURRENT  VARCHAR(1)   NOT NULL,
    IS_UPDATE_DATA    VARCHAR(1)   NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1)   NOT NULL,
    JOB_DATA          BLOB         NULL,
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR(120) NOT NULL,
    TRIGGER_NAME   VARCHAR(200) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    JOB_NAME       VARCHAR(200) NOT NULL,
    JOB_GROUP      VARCHAR(200) NOT NULL,
    DESCRIPTION    VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13)   NULL,
    PREV_FIRE_TIME BIGINT(13)   NULL,
    PRIORITY       INTEGER      NULL,
    TRIGGER_STATE  VARCHAR(16)  NOT NULL,
    TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
    START_TIME     BIGINT(13)   NOT NULL,
    END_TIME       BIGINT(13)   NULL,
    CALENDAR_NAME  VARCHAR(200) NULL,
    MISFIRE_INSTR  SMALLINT(2)  NULL,
    JOB_DATA       BLOB         NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    REPEAT_COUNT    BIGINT(7)    NOT NULL,
    REPEAT_INTERVAL BIGINT(12)   NOT NULL,
    TIMES_TRIGGERED BIGINT(10)   NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(200) NOT NULL,
    TIME_ZONE_ID    VARCHAR(80),
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    VARCHAR(120)   NOT NULL,
    TRIGGER_NAME  VARCHAR(200)   NOT NULL,
    TRIGGER_GROUP VARCHAR(200)   NOT NULL,
    STR_PROP_1    VARCHAR(512)   NULL,
    STR_PROP_2    VARCHAR(512)   NULL,
    STR_PROP_3    VARCHAR(512)   NULL,
    INT_PROP_1    INT            NULL,
    INT_PROP_2    INT            NULL,
    LONG_PROP_1   BIGINT         NULL,
    LONG_PROP_2   BIGINT         NULL,
    DEC_PROP_1    NUMERIC(13, 4) NULL,
    DEC_PROP_2    NUMERIC(13, 4) NULL,
    BOOL_PROP_1   VARCHAR(1)     NULL,
    BOOL_PROP_2   VARCHAR(1)     NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA     BLOB         NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    CALENDAR_NAME VARCHAR(200) NOT NULL,
    CALENDAR      BLOB         NOT NULL,
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    ENTRY_ID          VARCHAR(95)  NOT NULL,
    TRIGGER_NAME      VARCHAR(200) NOT NULL,
    TRIGGER_GROUP     VARCHAR(200) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    FIRED_TIME        BIGINT(13)   NOT NULL,
    SCHED_TIME        BIGINT(13)   NOT NULL,
    PRIORITY          INTEGER      NOT NULL,
    STATE             VARCHAR(16)  NOT NULL,
    JOB_NAME          VARCHAR(200) NULL,
    JOB_GROUP         VARCHAR(200) NULL,
    IS_NONCONCURRENT  VARCHAR(1)   NULL,
    REQUESTS_RECOVERY VARCHAR(1)   NULL,
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13)   NOT NULL,
    CHECKIN_INTERVAL  BIGINT(13)   NOT NULL,
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40)  NOT NULL,
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '字典ID',
    `dict_key`    bigint(20)   NOT NULL COMMENT '键',
    `dict_value`  varchar(100) NOT NULL COMMENT '值',
    `field_name`  varchar(100) NOT NULL COMMENT '字段名称',
    `table_name`  varchar(100) NOT NULL COMMENT '表名',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `modify_time` datetime              default null comment '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `bean_name`   varchar(100) NOT NULL COMMENT 'spring bean名称',
    `method_name` varchar(100) NOT NULL COMMENT '方法名',
    `params`      varchar(200)          DEFAULT NULL COMMENT '参数',
    `cron_exp`    varchar(100) NOT NULL COMMENT 'cron表达式',
    `status`      char(1)      NOT NULL COMMENT '任务状态 0: 正常 1: 暂停',
    `remark`      varchar(200)          DEFAULT NULL COMMENT '备注',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_job_log`;
CREATE TABLE `t_job_log`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
    `job_id`      bigint(20) NOT NULL COMMENT '任务id',
    `status`      char(2)    NOT NULL COMMENT '任务状态 0：成功 1：失败',
    `error`       text           DEFAULT NULL COMMENT '失败信息',
    `times`       decimal(11, 0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
    `create_time` datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `username`    varchar(50)    DEFAULT NULL COMMENT '操作用户',
    `operation`   text           DEFAULT NULL COMMENT '操作内容',
    `time`        decimal(11, 0) DEFAULT NULL COMMENT '耗时',
    `method`      text           DEFAULT NULL COMMENT '操作方法',
    `params`      text           DEFAULT NULL COMMENT '方法参数',
    `ip`          varchar(64)    DEFAULT NULL COMMENT '操作者IP',
    `location`    varchar(50)    DEFAULT NULL COMMENT '操作地点',
    `create_time` datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`
(
    `username`   varchar(50) NOT NULL COMMENT '用户名',
    `login_time` datetime    NOT NULL COMMENT '登录时间',
    `ip`         varchar(100) DEFAULT NULL COMMENT 'IP地址',
    `location`   varchar(255) DEFAULT NULL COMMENT '登录地点'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
    `parent_id`   bigint(20)  NOT NULL COMMENT '上级菜单ID',
    `name`        varchar(50) NOT NULL COMMENT '菜单/按钮名称',
    `path`        varchar(255)  DEFAULT NULL COMMENT '对应路由path',
    `component`   varchar(255)  DEFAULT NULL COMMENT '对应路由组件component',
    `perms`       varchar(50)   DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(50)   DEFAULT NULL COMMENT '图标',
    `type`        char(2)     NOT NULL COMMENT '类型 0菜单 1按钮',
    `order_num`   double(20, 0) DEFAULT NULL COMMENT '排序',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime      DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        varchar(10) NOT NULL COMMENT '角色名称',
    `remark`      varchar(100)         DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime             DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`
(
    `role_id` bigint(20) NOT NULL,
    `menu_id` bigint(20) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test`
(
    `id`          bigint(11)   NOT NULL AUTO_INCREMENT,
    `field1`      varchar(20)  NOT NULL,
    `field2`      int(11)      NOT NULL,
    `field3`      varchar(100) NOT NULL,
    `create_time` datetime     NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`        varchar(50)  NOT NULL COMMENT '用户名',
    `password`        varchar(128) NOT NULL COMMENT '密码',
    `email`           varchar(128) DEFAULT NULL COMMENT '邮箱',
    `mobile`          varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `status`          char(1)      NOT NULL COMMENT '状态 0锁定 1有效',
    `sex`             char(1)      DEFAULT NULL COMMENT '性别 0男 1女 2保密',
    `description`     varchar(100) DEFAULT NULL COMMENT '描述',
    `avatar`          varchar(100) DEFAULT NULL COMMENT '用户头像',
    `last_login_time` datetime     DEFAULT NULL COMMENT '最近访问时间',
    `create_time`     datetime     NOT NULL COMMENT '创建时间',
    `modify_time`     datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_username` (`username` ASC),
    UNIQUE INDEX `u_email` (`email` ASC),
    UNIQUE INDEX `u_mobile` (`mobile` ASC)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_user_config
-- ----------------------------
DROP TABLE IF EXISTS `t_user_config`;
CREATE TABLE `t_user_config`
(
    `user_id`     bigint(20) NOT NULL COMMENT '用户ID',
    `theme`       varchar(10) DEFAULT NULL COMMENT '系统主题 dark暗色风格，light明亮风格',
    `layout`      varchar(10) DEFAULT NULL COMMENT '系统布局 side侧边栏，head顶部栏',
    `multi_page`  char(1)     DEFAULT NULL COMMENT '页面风格 1多标签页 0单页',
    `fix_sidebar` char(1)     DEFAULT NULL COMMENT '页面滚动是否固定侧边栏 1固定 0不固定',
    `fix_header`  char(1)     DEFAULT NULL COMMENT '页面滚动是否固定顶栏 1固定 0不固定',
    `color`       varchar(20) DEFAULT NULL COMMENT '主题颜色 RGB值',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`
(
    `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
    `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table structure for t_file_category
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_file_category`;
CREATE TABLE `t_file_category`
(
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50) NOT NULL DEFAULT '',
    `create_time` DATETIME    NOT NULL DEFAULT current_timestamp,
    `modify_time` DATETIME             DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_name` (`name` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    ROW_FORMAT = DYNAMIC
    COMMENT = '文件分类';


-- -----------------------------------------------------
-- Table structure for t_file
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`
(
    `id`             BIGINT(20)    NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`           VARCHAR(256)  NULL     DEFAULT '' COMMENT '文件名',
    `suffix`         VARCHAR(16)   NOT NULL DEFAULT '' COMMENT '文件后缀',
    `local_url`      VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '本地路径',
    `visit_url`      VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '客户端访问路径',
    `size`           BIGINT(20)    NOT NULL DEFAULT 0 COMMENT '文件大小，单位bit',
    `description`    VARCHAR(1024) NULL     DEFAULT '' COMMENT '文件描述',
    `view_perms`     CHAR(1)       NOT NULL DEFAULT 1 COMMENT '查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看',
    `download_perms` CHAR(1)       NOT NULL DEFAULT 1 COMMENT '下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载',
    `check_times`    BIGINT(20)    NOT NULL DEFAULT 0 COMMENT '查看次数',
    `download_times` BIGINT(20)    NOT NULL DEFAULT 0 COMMENT '下载次数',
    `tag`            VARCHAR(45)   NULL     DEFAULT '' COMMENT '文件标签',
    `uploader_id`    BIGINT(20)    NOT NULL COMMENT '文件上传者',
    `category_id`    BIGINT(20)    NOT NULL COMMENT '分类Id',
    `upload_time`    DATETIME      NOT NULL DEFAULT current_timestamp COMMENT '上传时间',
    `modify_time`    DATETIME      NOT NULL DEFAULT current_timestamp COMMENT '最近一次修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_file_id` (`id` ASC),
    INDEX `fk_file_user_idx` (`uploader_id` ASC),
    CONSTRAINT `fk_file_user`
        FOREIGN KEY (`uploader_id`)
            REFERENCES `t_user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    INDEX `fk_file_category_idx` (`category_id` ASC),
    CONSTRAINT `fk_file_category1`
        FOREIGN KEY (`category_id`)
            REFERENCES `t_file_category` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    ROW_FORMAT = DYNAMIC
    COMMENT = '文件列表';


-- -----------------------------------------------------
-- Table structure for t_file_auth
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_file_auth`;
CREATE TABLE `t_file_auth`
(
    `id`             BIGINT(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `role_id`        BIGINT(20) NOT NULL COMMENT '角色Id',
    `view_perms`     CHAR(1)    NOT NULL COMMENT '查看权限: 0游客不允许 1游客允许 2用户允许 3会员查看',
    `download_perms` CHAR(1)    NOT NULL COMMENT '下载权限: 0游客不允许 1游客下载 2用户允许 3会员下载',
    `create_time`    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time`    datetime            DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_role_file_auth_idx` (`role_id` ASC),
    CONSTRAINT `fk_role_file_auth_idx`
        FOREIGN KEY (`role_id`)
            REFERENCES `t_role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    ROW_FORMAT = DYNAMIC
    COMMENT '文件权限表';


-- -----------------------------------------------------
-- Table structure for t_file_download_log
-- -----------------------------------------------------
DROP TABLE IF EXISTS `t_file_download_log`;
CREATE TABLE `t_file_download_log`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id`     BIGINT(20) NOT NULL,
    `file_id`     BIGINT(20) NOT NULL,
    `create_time` DATETIME   NOT NULL DEFAULT current_timestamp COMMENT '下载时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_download_user_idx` (`user_id` ASC),
    CONSTRAINT `fk_download_user_idx`
        FOREIGN KEY (`user_id`)
            REFERENCES `t_user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    INDEX `fk_download_file_idx` (`file_id` ASC),
    CONSTRAINT `fk_download_file_idx`
        FOREIGN KEY (`file_id`)
            REFERENCES `t_file` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    ROW_FORMAT = DYNAMIC
    COMMENT = '下载历史表';

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


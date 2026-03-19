-- HQZP Recruit Platform — DDL
-- MySQL 8.0+, charset utf8mb4

CREATE DATABASE IF NOT EXISTS hqzp_recruit DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hqzp_recruit;

-- -------------------------------------------------------
-- sys_user
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS sys_user (
    id            BIGINT       NOT NULL COMMENT '主键',
    username      VARCHAR(50)  NOT NULL COMMENT '用户名',
    password      VARCHAR(100) NOT NULL COMMENT '密码(BCrypt)',
    nickname      VARCHAR(50)  COMMENT '昵称',
    avatar        VARCHAR(500) COMMENT '头像URL',
    email         VARCHAR(100) COMMENT '邮箱',
    phone         VARCHAR(20)  COMMENT '手机号',
    user_type     TINYINT      NOT NULL DEFAULT 3 COMMENT '1=admin 2=hr 3=candidate',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '0=禁用 1=正常',
    company_id    BIGINT       COMMENT 'HR所属公司ID',
    create_by     BIGINT       COMMENT '创建人',
    update_by     BIGINT       COMMENT '更新人',
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       TINYINT      NOT NULL DEFAULT 0 COMMENT '0=正常 1=已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_phone (phone),
    KEY idx_company (company_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- -------------------------------------------------------
-- company
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS company (
    id               BIGINT       NOT NULL,
    name             VARCHAR(100) NOT NULL COMMENT '公司名称',
    logo             VARCHAR(500) COMMENT '公司Logo',
    description      TEXT         COMMENT '公司简介',
    industry         VARCHAR(50)  COMMENT '行业',
    scale            TINYINT      COMMENT '规模 1<20 2=20-99 3=100-499 4=500-999 5=1000+',
    financing_stage  VARCHAR(20)  COMMENT '融资阶段',
    city             VARCHAR(50)  COMMENT '城市',
    address          VARCHAR(200) COMMENT '详细地址',
    website          VARCHAR(200) COMMENT '官网',
    verify_status    TINYINT      NOT NULL DEFAULT 0 COMMENT '0=待审核 1=已认证 2=已拒绝',
    create_by        BIGINT,
    update_by        BIGINT,
    create_time      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted          TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_city (city),
    KEY idx_industry (industry)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息';

-- -------------------------------------------------------
-- job
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS job (
    id             BIGINT         NOT NULL,
    company_id     BIGINT         NOT NULL COMMENT '所属公司',
    publisher_id   BIGINT         NOT NULL COMMENT '发布HR',
    title          VARCHAR(100)   NOT NULL COMMENT '职位名称',
    description    TEXT           COMMENT '职位描述',
    requirement    TEXT           COMMENT '任职要求',
    city           VARCHAR(50)    COMMENT '工作城市',
    address        VARCHAR(200)   COMMENT '详细地址',
    salary_min     DECIMAL(10,2)  COMMENT '薪资下限(元/月)',
    salary_max     DECIMAL(10,2)  COMMENT '薪资上限(元/月)',
    experience     VARCHAR(20)    COMMENT '经验要求',
    education      VARCHAR(20)    COMMENT '学历要求',
    category       VARCHAR(50)    COMMENT '职位类别',
    headcount      INT            DEFAULT 1 COMMENT '招聘人数',
    job_type       TINYINT        DEFAULT 1 COMMENT '1=全职 2=兼职 3=实习',
    status         TINYINT        NOT NULL DEFAULT 0 COMMENT '0=草稿 1=发布 2=关闭',
    view_count     INT            NOT NULL DEFAULT 0,
    delivery_count INT            NOT NULL DEFAULT 0,
    create_by      BIGINT,
    update_by      BIGINT,
    create_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted        TINYINT        NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_company (company_id),
    KEY idx_city_status (city, status),
    KEY idx_category (category),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位信息';

-- -------------------------------------------------------
-- resume
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS resume (
    id               BIGINT       NOT NULL,
    user_id          BIGINT       NOT NULL COMMENT '所属用户',
    real_name        VARCHAR(50)  COMMENT '真实姓名',
    gender           TINYINT      COMMENT '0=未知 1=男 2=女',
    age              INT          COMMENT '年龄',
    phone            VARCHAR(20)  COMMENT '手机号',
    email            VARCHAR(100) COMMENT '邮箱',
    city             VARCHAR(50)  COMMENT '所在城市',
    avatar           VARCHAR(500) COMMENT '头像',
    current_title    VARCHAR(100) COMMENT '当前职位',
    experience_years INT          COMMENT '工作年限',
    education        VARCHAR(20)  COMMENT '最高学历',
    summary          TEXT         COMMENT '自我介绍',
    ai_score         INT          COMMENT 'AI评分(0-100)',
    ai_analysis      TEXT         COMMENT 'AI分析结果(JSON)',
    attachment_key   VARCHAR(500) COMMENT '附件S3 Key',
    attachment_name  VARCHAR(200) COMMENT '附件原始文件名',
    visibility       TINYINT      NOT NULL DEFAULT 1 COMMENT '0=私密 1=公开',
    create_by        BIGINT,
    update_by        BIGINT,
    create_time      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted          TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历';

-- -------------------------------------------------------
-- resume_work_exp
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS resume_work_exp (
    id           BIGINT       NOT NULL,
    resume_id    BIGINT       NOT NULL,
    company_name VARCHAR(100) COMMENT '公司名称',
    job_title    VARCHAR(100) COMMENT '职位名称',
    start_date   DATE         COMMENT '开始时间',
    end_date     DATE         COMMENT '结束时间',
    is_current   TINYINT      DEFAULT 0 COMMENT '是否在职',
    description  TEXT         COMMENT '工作描述',
    create_by    BIGINT,
    update_by    BIGINT,
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted      TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_resume (resume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作经历';

-- -------------------------------------------------------
-- resume_edu_exp
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS resume_edu_exp (
    id          BIGINT       NOT NULL,
    resume_id   BIGINT       NOT NULL,
    school_name VARCHAR(100) COMMENT '学校名称',
    major       VARCHAR(100) COMMENT '专业',
    degree      VARCHAR(20)  COMMENT '学历',
    start_date  DATE         COMMENT '开始时间',
    end_date    DATE         COMMENT '结束时间',
    description TEXT         COMMENT '描述',
    create_by   BIGINT,
    update_by   BIGINT,
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_resume (resume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教育经历';

-- -------------------------------------------------------
-- job_application
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS job_application (
    id                 BIGINT   NOT NULL,
    job_id             BIGINT   NOT NULL,
    candidate_id       BIGINT   NOT NULL,
    resume_id          BIGINT   NOT NULL,
    company_id         BIGINT   NOT NULL,
    status             TINYINT  NOT NULL DEFAULT 0 COMMENT '0=待查看 1=已查看 2=邀请面试 3=面试中 4=已录用 5=已拒绝 6=已放弃',
    hr_remark          TEXT     COMMENT 'HR备注',
    ai_match_score     INT      COMMENT 'AI匹配分(0-100)',
    ai_match_analysis  TEXT     COMMENT 'AI匹配分析(JSON)',
    view_time          DATETIME COMMENT 'HR查看时间',
    interview_time     DATETIME COMMENT '面试时间',
    interview_location VARCHAR(300) COMMENT '面试地点/链接',
    create_by          BIGINT,
    update_by          BIGINT,
    create_time        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted            TINYINT  NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_job_candidate (job_id, candidate_id),
    KEY idx_candidate (candidate_id),
    KEY idx_company_status (company_id, status),
    KEY idx_job (job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投递记录';

-- -------------------------------------------------------
-- sys_file
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS sys_file (
    id            BIGINT       NOT NULL,
    file_key      VARCHAR(500) NOT NULL COMMENT 'S3 Object Key',
    original_name VARCHAR(200) COMMENT '原始文件名',
    content_type  VARCHAR(100) COMMENT 'MIME类型',
    file_size     BIGINT       COMMENT '文件大小(字节)',
    bucket        VARCHAR(100) COMMENT 'S3 Bucket',
    url           VARCHAR(500) COMMENT '访问URL',
    biz_type      VARCHAR(50)  COMMENT '业务类型',
    biz_id        BIGINT       COMMENT '业务ID',
    create_by     BIGINT,
    update_by     BIGINT,
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_biz (biz_type, biz_id),
    KEY idx_file_key (file_key(100))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件记录';

-- -------------------------------------------------------
-- ai_chat_record
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS ai_chat_record (
    id                BIGINT   NOT NULL,
    user_id           BIGINT   COMMENT '用户ID',
    scene             VARCHAR(50)  COMMENT '场景',
    biz_id            BIGINT   COMMENT '业务ID',
    prompt            TEXT     COMMENT '提示词',
    response          LONGTEXT COMMENT 'AI响应',
    model             VARCHAR(50)  COMMENT '模型名称',
    prompt_tokens     INT      COMMENT 'Prompt tokens',
    completion_tokens INT      COMMENT 'Completion tokens',
    total_tokens      INT      COMMENT 'Total tokens',
    elapsed_ms        BIGINT   COMMENT '耗时(ms)',
    status            TINYINT  NOT NULL DEFAULT 1 COMMENT '0=失败 1=成功',
    error_message     VARCHAR(500) COMMENT '错误信息',
    create_by         BIGINT,
    update_by         BIGINT,
    create_time       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted           TINYINT  NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_user_scene (user_id, scene),
    KEY idx_biz (biz_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话记录';

-- -------------------------------------------------------
-- Seed: default admin user (password: Admin@123)
-- -------------------------------------------------------
INSERT IGNORE INTO sys_user (id, username, password, nickname, user_type, status, deleted, create_time, update_time)
VALUES (1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 1, 1, 0, NOW(), NOW());

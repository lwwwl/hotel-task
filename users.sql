-- Creating the hotel_users table
CREATE TABLE hotel_users
(
    id              BIGSERIAL PRIMARY KEY COMMENT '用户ID（主键）',
    username        VARCHAR(50)  NOT NULL COMMENT '用户名',
    password        VARCHAR(100) NOT NULL COMMENT '加密后的密码',
    display_name    VARCHAR(100) COMMENT '姓名',
    employee_number VARCHAR(50) COMMENT '工号',
    email           VARCHAR(100) COMMENT '邮箱地址',
    phone           VARCHAR(20) COMMENT '手机号',
    extra_infos     VARCHAR(1000) COMMENT '额外信息，三方通知管道等',
    active          SMALLINT  DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX           idx_hotel_users_username (username),
    INDEX           idx_hotel_users_email (email),
    INDEX           idx_hotel_users_phone (phone),
    INDEX           idx_hotel_users_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客服表';

-- Creating the hotel_departments table
CREATE TABLE hotel_departments
(
    id             BIGSERIAL PRIMARY KEY COMMENT '部门ID（主键）',
    name           VARCHAR(100) NOT NULL COMMENT '部门名称',
    parent_id      BIGINT    DEFAULT 0 COMMENT '上级部门ID（顶级为0）',
    leader_user_id BIGINT COMMENT '部门领导用户id',
    member_count   INT       DEFAULT 0 COMMENT '人数',
    create_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX          idx_hotel_departments_parent_id (parent_id),
    INDEX          idx_hotel_departments_leader_user_id (leader_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- Creating the hotel_user_department table
CREATE TABLE hotel_user_department
(
    id          BIGSERIAL PRIMARY KEY COMMENT '主键',
    user_id     BIGINT NOT NULL COMMENT '用户ID',
    dept_id     BIGINT NOT NULL COMMENT '部门ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX       idx_hotel_user_department_user_id (user_id),
    INDEX       idx_hotel_user_department_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户部门关联表';

-- Creating the hotel_roles table
CREATE TABLE hotel_roles
(
    id           BIGSERIAL PRIMARY KEY COMMENT '角色ID（主键）',
    name         VARCHAR(50) NOT NULL COMMENT '角色名称',
    description  VARCHAR(200) COMMENT '角色描述',
    member_count INT       DEFAULT 0 COMMENT '人数',
    create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX        idx_hotel_roles_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- Creating the hotel_user_role table
CREATE TABLE hotel_user_role
(
    id          BIGSERIAL PRIMARY KEY COMMENT '主键',
    user_id     BIGINT NOT NULL COMMENT '用户ID',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX       idx_hotel_user_role_user_id (user_id),
    INDEX       idx_hotel_user_role_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- Creating the hotel_menus table
CREATE TABLE hotel_menus
(
    id          BIGSERIAL PRIMARY KEY COMMENT '菜单ID（主键）',
    parent_id   BIGINT    DEFAULT 0 COMMENT '父菜单ID（顶级为0）',
    name        VARCHAR(100) NOT NULL COMMENT '菜单名称',
    path        VARCHAR(200) COMMENT '路径/URL',
    component   VARCHAR(200) COMMENT '前端组件路径',
    type        SMALLINT     NOT NULL COMMENT '菜单类型（0-目录，1-菜单，2-按钮）',
    icon        VARCHAR(50) COMMENT '图标',
    sort_order  INT       DEFAULT 0 COMMENT '排序',
    visible     BOOLEAN   DEFAULT TRUE COMMENT '是否可见',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX       idx_hotel_menus_parent_id (parent_id),
    INDEX       idx_hotel_menus_type (type),
    INDEX       idx_hotel_menus_visible (visible)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- Creating the hotel_role_menu table
CREATE TABLE hotel_role_menu
(
    id          BIGSERIAL PRIMARY KEY COMMENT '主键',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    menu_id     BIGINT NOT NULL COMMENT '菜单ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX       idx_hotel_role_menu_role_id (role_id),
    INDEX       idx_hotel_role_menu_menu_id (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

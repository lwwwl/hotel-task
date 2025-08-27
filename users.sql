-- hotel_users 表
CREATE TABLE hotel_users
(
    id                  BIGSERIAL PRIMARY KEY,
    username            VARCHAR(50)  NOT NULL,
    password            VARCHAR(100) NOT NULL,
    display_name        VARCHAR(100),
    employee_number     VARCHAR(50),
    email               VARCHAR(100),
    phone               VARCHAR(20),
    cw_user_id          BIGINT,
    cw_api_access_token VARCHAR(250),
    super_admin         BOOLEAN   DEFAULT FALSE,
    extra_infos         VARCHAR(1000),
    active              SMALLINT  DEFAULT 1,
    create_time         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_users IS '客服表';
COMMENT ON COLUMN hotel_users.id IS '用户ID（主键）';
COMMENT ON COLUMN hotel_users.username IS '用户名';
COMMENT ON COLUMN hotel_users.password IS '加密后的密码';
COMMENT ON COLUMN hotel_users.display_name IS '姓名';
COMMENT ON COLUMN hotel_users.employee_number IS '工号';
COMMENT ON COLUMN hotel_users.email IS '邮箱地址';
COMMENT ON COLUMN hotel_users.phone IS '手机号';
COMMENT ON COLUMN hotel_users.cw_user_id IS 'chatwoot用户id';
COMMENT ON COLUMN hotel_users.cw_api_access_token IS 'chatwoot api access token';
COMMENT ON COLUMN hotel_users.super_admin IS '是否为超级管理员';
COMMENT ON COLUMN hotel_users.extra_infos IS '额外信息，三方通知管道等';
COMMENT ON COLUMN hotel_users.active IS '状态（0-禁用，1-启用）';
COMMENT ON COLUMN hotel_users.create_time IS '创建时间';
COMMENT ON COLUMN hotel_users.update_time IS '更新时间';

CREATE INDEX idx_hotel_users_username ON hotel_users (username);
CREATE INDEX idx_hotel_users_email ON hotel_users (email);
CREATE INDEX idx_hotel_users_phone ON hotel_users (phone);
CREATE INDEX idx_hotel_users_active ON hotel_users (active);

-- hotel_departments 表
CREATE TABLE hotel_departments
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    parent_id      BIGINT                DEFAULT 0,
    leader_user_id BIGINT,
    member_count   INT          NOT NULL DEFAULT 0,
    create_time    TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    update_time    TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_departments IS '部门表';
COMMENT ON COLUMN hotel_departments.id IS '部门ID（主键）';
COMMENT ON COLUMN hotel_departments.name IS '部门名称';
COMMENT ON COLUMN hotel_departments.parent_id IS '上级部门ID（顶级为0）';
COMMENT ON COLUMN hotel_departments.leader_user_id IS '部门领导用户id';
COMMENT ON COLUMN hotel_departments.member_count IS '人数';
COMMENT ON COLUMN hotel_departments.create_time IS '创建时间';
COMMENT ON COLUMN hotel_departments.update_time IS '更新时间';

CREATE INDEX idx_hotel_departments_parent_id ON hotel_departments (parent_id);
CREATE INDEX idx_hotel_departments_leader_user_id ON hotel_departments (leader_user_id);

-- hotel_user_department 表
CREATE TABLE hotel_user_department
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    dept_id     BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_user_department IS '用户部门关联表';
COMMENT ON COLUMN hotel_user_department.id IS '主键';
COMMENT ON COLUMN hotel_user_department.user_id IS '用户ID';
COMMENT ON COLUMN hotel_user_department.dept_id IS '部门ID';
COMMENT ON COLUMN hotel_user_department.create_time IS '创建时间';
COMMENT ON COLUMN hotel_user_department.update_time IS '更新时间';

CREATE INDEX idx_hotel_user_department_user_id ON hotel_user_department (user_id);
CREATE INDEX idx_hotel_user_department_dept_id ON hotel_user_department (dept_id);

-- hotel_roles 表
CREATE TABLE hotel_roles
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(50) NOT NULL,
    description  VARCHAR(200),
    member_count INT       DEFAULT 0,
    create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_roles IS '角色表';
COMMENT ON COLUMN hotel_roles.id IS '角色ID（主键）';
COMMENT ON COLUMN hotel_roles.name IS '角色名称';
COMMENT ON COLUMN hotel_roles.description IS '角色描述';
COMMENT ON COLUMN hotel_roles.member_count IS '人数';
COMMENT ON COLUMN hotel_roles.create_time IS '创建时间';
COMMENT ON COLUMN hotel_roles.update_time IS '更新时间';

CREATE INDEX idx_hotel_roles_name ON hotel_roles (name);

-- hotel_user_role 表
CREATE TABLE hotel_user_role
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    role_id     BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_user_role IS '用户角色关联表';
COMMENT ON COLUMN hotel_user_role.id IS '主键';
COMMENT ON COLUMN hotel_user_role.user_id IS '用户ID';
COMMENT ON COLUMN hotel_user_role.role_id IS '角色ID';
COMMENT ON COLUMN hotel_user_role.create_time IS '创建时间';
COMMENT ON COLUMN hotel_user_role.update_time IS '更新时间';

CREATE INDEX idx_hotel_user_role_user_id ON hotel_user_role (user_id);
CREATE INDEX idx_hotel_user_role_role_id ON hotel_user_role (role_id);

-- hotel_menus 表
CREATE TABLE hotel_menus
(
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT    DEFAULT 0,
    name        VARCHAR(100) NOT NULL,
    path        VARCHAR(200),
    component   VARCHAR(200),
    perms       VARCHAR(200),
    type        SMALLINT     NOT NULL,
    icon        VARCHAR(50),
    sort_order  INT       DEFAULT 0,
    visible     BOOLEAN   DEFAULT TRUE,
    remark      VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_menus IS '菜单表';
COMMENT ON COLUMN hotel_menus.id IS '菜单ID（主键）';
COMMENT ON COLUMN hotel_menus.parent_id IS '父菜单ID（顶级为0）';
COMMENT ON COLUMN hotel_menus.name IS '菜单名称';
COMMENT ON COLUMN hotel_menus.path IS '路径/URL';
COMMENT ON COLUMN hotel_menus.component IS '前端组件路径';
COMMENT ON COLUMN hotel_menus.perms IS '权限标识';
COMMENT ON COLUMN hotel_menus.type IS '菜单类型（0-目录，1-菜单，2-按钮）';
COMMENT ON COLUMN hotel_menus.icon IS '图标';
COMMENT ON COLUMN hotel_menus.sort_order IS '排序';
COMMENT ON COLUMN hotel_menus.visible IS '是否可见';
COMMENT ON COLUMN hotel_menus.remark IS '备注';
COMMENT ON COLUMN hotel_menus.create_time IS '创建时间';
COMMENT ON COLUMN hotel_menus.update_time IS '更新时间';

CREATE INDEX idx_hotel_menus_parent_id ON hotel_menus (parent_id);
CREATE INDEX idx_hotel_menus_type ON hotel_menus (type);
CREATE INDEX idx_hotel_menus_visible ON hotel_menus (visible);

-- hotel_role_menu 表
CREATE TABLE hotel_role_menu
(
    id          BIGSERIAL PRIMARY KEY,
    role_id     BIGINT NOT NULL,
    menu_id     BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_role_menu IS '角色菜单关联表';
COMMENT ON COLUMN hotel_role_menu.id IS '主键';
COMMENT ON COLUMN hotel_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN hotel_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN hotel_role_menu.create_time IS '创建时间';
COMMENT ON COLUMN hotel_role_menu.update_time IS '更新时间';

CREATE INDEX idx_hotel_role_menu_role_id ON hotel_role_menu (role_id);
CREATE INDEX idx_hotel_role_menu_menu_id ON hotel_role_menu (menu_id);

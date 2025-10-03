-- hotel_room 表
CREATE TABLE hotel_room
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128),
    active      SMALLINT  DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_room IS '房间表';
COMMENT ON COLUMN hotel_room.id IS '主键';
COMMENT ON COLUMN hotel_room.name IS '房间名称';
COMMENT ON COLUMN hotel_room.active IS '是否有效 0-无效 1-有效';
COMMENT ON COLUMN hotel_room.create_time IS '创建时间';
COMMENT ON COLUMN hotel_room.update_time IS '更新时间';

CREATE INDEX idx_hotel_room_name ON hotel_room (name);
CREATE INDEX idx_hotel_room_active ON hotel_room (active);

-- quick_menu 表
CREATE TABLE quick_menu
(
    id          BIGSERIAL PRIMARY KEY,
    icon        VARCHAR(128),
    sort_order  INTEGER,
    content     TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE quick_menu IS '快捷菜单表';
COMMENT ON COLUMN quick_menu.id IS '主键';
COMMENT ON COLUMN quick_menu.icon IS '图标';
COMMENT ON COLUMN quick_menu.sort_order IS '排序序号，越小越靠前';
COMMENT ON COLUMN quick_menu.content IS '存储title、body多种语言的信息';
COMMENT ON COLUMN quick_menu.create_time IS '创建时间';
COMMENT ON COLUMN quick_menu.update_time IS '更新时间';

-- message_translate 表
CREATE TABLE message_translate
(
    id              BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT,
    message_id      BIGINT,
    language        VARCHAR(32),
    content         TEXT,
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE message_translate IS '消息翻译结果表';
COMMENT ON COLUMN message_translate.conversation_id IS '会话id';
COMMENT ON COLUMN message_translate.language IS '语言';
COMMENT ON COLUMN message_translate.content IS '翻译结果';
COMMENT ON COLUMN message_translate.create_time IS '创建时间';
COMMENT ON COLUMN message_translate.update_time IS '更新时间';

CREATE INDEX idx_message_translate_conversation_id ON message_translate(conversation_id);
CREATE INDEX idx_message_translate_message_id ON message_translate(message_id);
-- 联合唯一索引：同一会话、同一消息、同一语言只存一条翻译
CREATE UNIQUE INDEX uniq_message_translate_cid_mid_lang
    ON message_translate(conversation_id, message_id, language);


-- hotel_task_notification 表
CREATE TABLE hotel_task_notification
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT,
    title             varchar(256),
    body              TEXT,
    task_id           BIGINT,
    notification_type VARCHAR(32) DEFAULT 'info',
    already_read      SMALLINT    DEFAULT 0,
    create_time       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    update_time       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE hotel_task_notification IS '任务通知表';
COMMENT ON COLUMN hotel_task_notification.user_id IS '用户id';
COMMENT ON COLUMN hotel_task_notification.title IS '通知标题';
COMMENT ON COLUMN hotel_task_notification.body IS '通知内容';
COMMENT ON COLUMN hotel_task_notification.task_id IS '任务id';
COMMENT ON COLUMN hotel_task_notification.notification_type IS '通知类型: info-普通通知, alert-提醒通知, success-成功通知';
COMMENT ON COLUMN hotel_task_notification.already_read IS '是否已读';
COMMENT ON COLUMN hotel_task_notification.create_time IS '创建时间';
COMMENT ON COLUMN hotel_task_notification.update_time IS '更新时间';

-- hotel_events 表
CREATE TABLE hotel_events
(
    id          BIGSERIAL PRIMARY KEY,
    event_type  VARCHAR(32),
    event_sub_type VARCHAR(32),
    event_content TEXT,
    event_level VARCHAR(32),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE hotel_events IS '事件表';
COMMENT ON COLUMN hotel_events.event_type IS '事件类型 task-任务事件, conversation-会话事件';
COMMENT ON COLUMN hotel_events.event_sub_type IS '事件子类型';
COMMENT ON COLUMN hotel_events.event_content IS '事件内容';
COMMENT ON COLUMN hotel_events.event_level IS '事件级别 info-普通事件, warning-提醒事件, error-错误事件';
COMMENT ON COLUMN hotel_events.create_time IS '创建时间';
COMMENT ON COLUMN hotel_events.update_time IS '更新时间';

-- Creating the hotel_tasks table
CREATE TABLE hotel_tasks
(
    id               BIGSERIAL PRIMARY KEY COMMENT '主键，工单id',
    title            VARCHAR(250) NOT NULL COMMENT '标题',
    description      TEXT COMMENT '描述',
    room_id          BIGINT COMMENT '房间号id',
    guest_id         BIGINT COMMENT '关联的客人id',
    dept_id          BIGINT COMMENT '关联的部门id',
    creator_user_id  BIGINT COMMENT '创建人客服id',
    executor_user_id BIGINT COMMENT '执行人客服id',
    conversation_id  BIGINT COMMENT '关联chatwoot会话id',
    deadline_time    TIMESTAMP COMMENT '到期时间',
    complete_time    TIMESTAMP COMMENT '完成时间',
    priority         INT COMMENT '优先级 1-低 2-中 3-高 4-紧急',
    task_status      INT COMMENT '执行状态 1-待处理 2-处理中 3-待确认 4-已完成',
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX            idx_hotel_tasks_room_id (room_id),
    INDEX            idx_hotel_tasks_guest_id (guest_id),
    INDEX            idx_hotel_tasks_dept_id (dept_id),
    INDEX            idx_hotel_tasks_creator_user_id (creator_user_id),
    INDEX            idx_hotel_tasks_executor_user_id (executor_user_id),
    INDEX            idx_hotel_tasks_task_status (task_status),
    INDEX            idx_hotel_tasks_priority (priority),
    INDEX            idx_hotel_tasks_deadline_time (deadline_time),
    INDEX            idx_hotel_tasks_create_time (create_time),
    INDEX            idx_hotel_tasks_status_priority_deadline (task_status, priority, deadline_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单表';

-- Creating the hotel_task_operate_record table
CREATE TABLE hotel_task_operate_record
(
    id               BIGSERIAL PRIMARY KEY COMMENT '主键',
    task_id          BIGINT NOT NULL COMMENT '工单id',
    operator_user_id BIGINT COMMENT '操作人userId',
    operate_type     INT    NOT NULL COMMENT '操作类型 1-创建工单 2-领取工单 3-完成工单 4-确认完成工单 5-转移执行人',
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX            idx_hotel_task_operate_record_task_id (task_id),
    INDEX            idx_hotel_task_operate_record_operator_user_id (operator_user_id),
    INDEX            idx_hotel_task_operate_record_operate_type (operate_type),
    INDEX            idx_hotel_task_operate_record_create_time (create_time),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工单操作日志表';

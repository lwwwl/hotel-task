-- hotel_tasks 表
CREATE TABLE hotel_tasks
(
    id                 BIGSERIAL PRIMARY KEY,
    title              VARCHAR(250) NOT NULL,
    description        TEXT,
    room_id            BIGINT,
    guest_id           BIGINT,
    dept_id            BIGINT,
    creator_user_id    BIGINT,
    executor_user_id   BIGINT,
    conversation_id    BIGINT,
    deadline_time      TIMESTAMP,
    start_process_time TIMESTAMP,
    complete_time      TIMESTAMP,
    priority           VARCHAR(250),
    task_status        VARCHAR(250),
    create_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_tasks IS '工单表';
COMMENT ON COLUMN hotel_tasks.id IS '主键，工单id';
COMMENT ON COLUMN hotel_tasks.title IS '标题';
COMMENT ON COLUMN hotel_tasks.description IS '描述';
COMMENT ON COLUMN hotel_tasks.room_id IS '房间号id';
COMMENT ON COLUMN hotel_tasks.guest_id IS '关联的客人id';
COMMENT ON COLUMN hotel_tasks.dept_id IS '关联的部门id';
COMMENT ON COLUMN hotel_tasks.creator_user_id IS '创建人客服id';
COMMENT ON COLUMN hotel_tasks.executor_user_id IS '执行人客服id';
COMMENT ON COLUMN hotel_tasks.conversation_id IS '关联chatwoot会话id';
COMMENT ON COLUMN hotel_tasks.deadline_time IS '到期时间';
COMMENT ON COLUMN hotel_tasks.start_process_time IS '开始处理时间';
COMMENT ON COLUMN hotel_tasks.complete_time IS '完成时间';
COMMENT ON COLUMN hotel_tasks.priority IS '优先级 low-低 medium-中 high-高 urgent-紧急';
COMMENT ON COLUMN hotel_tasks.task_status IS '执行状态 pending-待处理 in_progress-进行中 review-待确认 completed-已完成';
COMMENT ON COLUMN hotel_tasks.create_time IS '创建时间';
COMMENT ON COLUMN hotel_tasks.update_time IS '更新时间';

CREATE INDEX idx_hotel_tasks_room_id ON hotel_tasks (room_id);
CREATE INDEX idx_hotel_tasks_guest_id ON hotel_tasks (guest_id);
CREATE INDEX idx_hotel_tasks_dept_id ON hotel_tasks (dept_id);
CREATE INDEX idx_hotel_tasks_creator_user_id ON hotel_tasks (creator_user_id);
CREATE INDEX idx_hotel_tasks_executor_user_id ON hotel_tasks (executor_user_id);
CREATE INDEX idx_hotel_tasks_task_status ON hotel_tasks (task_status);
CREATE INDEX idx_hotel_tasks_priority ON hotel_tasks (priority);
CREATE INDEX idx_hotel_tasks_deadline_time ON hotel_tasks (deadline_time);
CREATE INDEX idx_hotel_tasks_create_time ON hotel_tasks (create_time);
CREATE INDEX idx_hotel_tasks_status_priority_deadline ON hotel_tasks (task_status, priority, deadline_time);

-- hotel_task_operate_record 表
CREATE TABLE hotel_task_operate_record
(
    id               BIGSERIAL PRIMARY KEY,
    task_id          BIGINT NOT NULL,
    operator_user_id BIGINT,
    operate_type     VARCHAR(250) NOT NULL,
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE hotel_task_operate_record IS '工单操作日志表';
COMMENT ON COLUMN hotel_task_operate_record.id IS '主键';
COMMENT ON COLUMN hotel_task_operate_record.task_id IS '工单id';
COMMENT ON COLUMN hotel_task_operate_record.operator_user_id IS '操作人userId';
COMMENT ON COLUMN hotel_task_operate_record.operate_type IS '操作类型 create-创建工单 claim-领取工单 review-待确认完成工单 complete-完成工单 transfer-转移执行人 update-更新工单 delete-删除工单 remind-催办工单 start_process-开始处理工单';
COMMENT ON COLUMN hotel_task_operate_record.create_time IS '创建时间';
COMMENT ON COLUMN hotel_task_operate_record.update_time IS '更新时间';

CREATE INDEX idx_hotel_task_operate_record_task_id ON hotel_task_operate_record (task_id);
CREATE INDEX idx_hotel_task_operate_record_operator_user_id ON hotel_task_operate_record (operator_user_id);
CREATE INDEX idx_hotel_task_operate_record_operate_type ON hotel_task_operate_record (operate_type);
CREATE INDEX idx_hotel_task_operate_record_create_time ON hotel_task_operate_record (create_time);

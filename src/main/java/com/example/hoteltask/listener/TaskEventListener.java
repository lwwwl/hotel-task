package com.example.hoteltask.listener;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.hoteltask.enums.TaskStatusEnum;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.service.TaskService;

@Component("taskEventListener")
public class TaskEventListener implements ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(TaskEventListener.class);

    @Autowired
    private TaskService taskService;

    @Override
    public void notify(DelegateExecution execution) {
        String activityId = execution.getCurrentActivityId();
        FlowElement flowElement = execution.getCurrentFlowElement();
        String activityName = flowElement != null ? flowElement.getName() : "";
        logger.info("工单流程节点触发: id={}, name={}", activityId, activityName);

        // 获取流程变量
        Long taskId = execution.getVariable("taskId") instanceof Long ? (Long) execution.getVariable("taskId") : null;
        Long userId = execution.getVariable("userId") instanceof Long ? (Long) execution.getVariable("userId") : null;
        if (taskId == null || userId == null) {
            logger.warn("流程变量taskId或userId未设置，无法变更工单状态");
            return;
        }

        // 根据节点名称映射到TaskStatusEnum
        TaskStatusEnum statusEnum = null;
        if ("待处理".equals(activityName)) {
            statusEnum = TaskStatusEnum.PENDING;
        } else if ("进行中".equals(activityName)) {
            statusEnum = TaskStatusEnum.IN_PROGRESS;
        } else if ("待确认".equals(activityName)) {
            statusEnum = TaskStatusEnum.PENDING_CONFIRMATION;
        } else if ("已完成".equals(activityName)) {
            statusEnum = TaskStatusEnum.COMPLETED;
        }
        if (statusEnum == null) {
            logger.warn("未识别的节点名称: {}，跳过状态变更", activityName);
            return;
        }

        // 构造请求并调用changeStatus
        TaskChangeStatusRequest request = new TaskChangeStatusRequest();
        request.setTaskId(taskId);
        request.setNewTaskStatus(statusEnum.getCode());
        taskService.changeStatus(userId, request);
    }
} 
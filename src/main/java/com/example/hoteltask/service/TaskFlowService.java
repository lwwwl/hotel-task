package com.example.hoteltask.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hoteltask.dao.entity.HotelTask;
import com.example.hoteltask.dao.repository.HotelTaskRepository;
import com.example.hoteltask.enums.TaskStatusEnum;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.model.response.ApiResponse;

/**
 * 工单流程服务，负责Flowable流程的流转
 */
@Service
public class TaskFlowService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFlowService.class);

    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService flowableTaskService;
    
    @Autowired
    private HotelTaskRepository taskRepository;

    /**
     * 通过Flowable流程变更工单状态
     * 注意：此方法只负责流程流转，不负责业务状态更新
     */
    @Transactional
    public ResponseEntity<?> changeStatusByFlowable(Long userId, TaskChangeStatusRequest request) {
        // 1. 校验工单是否存在
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        if (!taskOpt.isPresent()) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        HotelTask task = taskOpt.get();

        // 2. 查询工单对应的流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        if (processInstanceId == null) {
            // 如果没有流程，启动新流程
            Map<String, Object> vars = new HashMap<>();
            vars.put("taskId", request.getTaskId());
            vars.put("userId", userId);
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("taskProcess", vars);
            processInstanceId = pi.getId();
            
            // 更新工单的流程实例ID
            task.setProcessInstanceId(processInstanceId);
            taskRepository.save(task);
            logger.info("为工单{}启动新流程: {}", request.getTaskId(), processInstanceId);
        }

        // 3. 查询当前流程中的任务
        List<Task> tasks = flowableTaskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (tasks.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error(400, "当前无可操作节点", "No Task"));
        }

        // 4. 根据目标状态找到需要流转到的节点
        String targetNodeName = getNodeNameByStatus(request.getNewTaskStatus());
        if (targetNodeName == null) {
            return ResponseEntity.ok(ApiResponse.error(400, "无效的目标状态", "Invalid Status"));
        }

        // 5. 判断当前节点与目标节点的关系，确定流转路径
        Task currentTask = tasks.get(0); // 当前任务
        String currentNodeName = currentTask.getName();
        logger.info("当前节点: {}, 目标节点: {}", currentNodeName, targetNodeName);

        // 6. 如果当前节点就是目标节点，无需流转
        if (currentNodeName.equals(targetNodeName)) {
            return ResponseEntity.ok(ApiResponse.success("工单已经处于" + targetNodeName + "状态"));
        }

        // 7. 流转流程
        // 设置流程变量
        Map<String, Object> vars = new HashMap<>();
        vars.put("taskId", request.getTaskId());
        vars.put("userId", userId);

        // 完成当前任务，推进流程
        flowableTaskService.complete(currentTask.getId(), vars);
        logger.info("完成任务: {}, 流程实例: {}", currentTask.getId(), processInstanceId);

        // 8. 返回成功
        return ResponseEntity.ok(ApiResponse.success("流程流转成功"));
    }

    /**
     * 根据状态码获取对应的节点名称
     */
    private String getNodeNameByStatus(Integer status) {
        if (status == null) {
            return null;
        }
        
        TaskStatusEnum statusEnum = TaskStatusEnum.getByCode(status);
        if (statusEnum == null) {
            return null;
        }
        
        switch (statusEnum) {
            case PENDING:
                return "待处理";
            case IN_PROGRESS:
                return "进行中";
            case PENDING_CONFIRMATION:
                return "待确认";
            case COMPLETED:
                return "已完成";
            default:
                return null;
        }
    }
} 
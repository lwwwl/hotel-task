package com.example.hoteltask.service;

import org.springframework.http.ResponseEntity;

import com.example.hoteltask.model.request.TaskAddExecutorRequest;
import com.example.hoteltask.model.request.TaskClaimRequest;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.model.request.TaskCreateRequest;
import com.example.hoteltask.model.request.TaskDeleteRequest;
import com.example.hoteltask.model.request.TaskDetailRequest;
import com.example.hoteltask.model.request.TaskListRequest;
import com.example.hoteltask.model.request.TaskReminderRequest;
import com.example.hoteltask.model.request.TaskTransferExecutorRequest;
import com.example.hoteltask.model.request.TaskUpdateRequest;

public interface TaskService {
    /**
     * 获取工单列表
     */
    ResponseEntity<?> getTaskList(Long userId, TaskListRequest request);
    
    /**
     * 获取工单详情
     */
    ResponseEntity<?> getTaskDetail(Long userId, TaskDetailRequest request);
    
    /**
     * 创建工单
     */
    ResponseEntity<?> createTask(Long userId, TaskCreateRequest request);
    
    /**
     * 更新工单信息
     */
    ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request);
    
    /**
     * 删除工单
     */
    ResponseEntity<?> deleteTask(Long userId, TaskDeleteRequest request);

    /**
     * 认领工单
     */
    ResponseEntity<?> claimTask(Long userId, TaskClaimRequest request);

    /**
     * 添加执行人
     */
    ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request);
    
    /**
     * 转移执行人
     */
    ResponseEntity<?> transferExecutor(Long userId, TaskTransferExecutorRequest request);
    
    /**
     * 变更工单状态
     */
    ResponseEntity<?> changeStatus(Long userId, TaskChangeStatusRequest request);
    
    /**
     * 发送工单催办
     */
    ResponseEntity<?> sendReminder(Long userId, TaskReminderRequest request);
    
    /**
     * 获取工单SLA看板数据
     */
    ResponseEntity<?> getTaskSLA(Long userId);

    /**
     * 获取工单总数
     */
    ResponseEntity<?> getTotalCount();
} 
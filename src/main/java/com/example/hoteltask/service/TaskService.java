package com.example.hoteltask.service;

import com.example.hoteltask.model.request.*;
import java.util.Map;

public interface TaskService {
    /**
     * 获取工单列表
     */
    Map<String, Object> getTaskList(Integer userId, TaskListRequest request);
    
    /**
     * 获取工单详情
     */
    Map<String, Object> getTaskDetail(Integer userId, TaskDetailRequest request);
    
    /**
     * 创建工单
     */
    String createTask(Integer userId, TaskCreateRequest request);
    
    /**
     * 更新工单信息
     */
    String updateTask(Integer userId, TaskUpdateRequest request);
    
    /**
     * 删除工单
     */
    String deleteTask(String userId, TaskDeleteRequest request);
    
    /**
     * 添加执行人
     */
    String addExecutor(String userId, TaskAddExecutorRequest request);
    
    /**
     * 转移执行人
     */
    String transferExecutor(Integer userId, TaskTransferExecutorRequest request);
    
    /**
     * 变更工单状态
     */
    String changeStatus(Integer userId, TaskChangeStatusRequest request);
    
    /**
     * 发送工单催办
     */
    String sendReminder(Integer userId, TaskReminderRequest request);
    
    /**
     * 获取工单SLA看板数据
     */
    Map<String, Object> getTaskSLA(Integer userId);
} 
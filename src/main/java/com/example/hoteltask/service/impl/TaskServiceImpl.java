package com.example.hoteltask.service.impl;

import com.example.hoteltask.dao.entity.HotelTask;
import com.example.hoteltask.dao.entity.HotelTaskOperateRecord;
import com.example.hoteltask.dao.repository.HotelTaskOperateRecordRepository;
import com.example.hoteltask.dao.repository.HotelTaskRepository;
import com.example.hoteltask.model.request.*;
import com.example.hoteltask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private HotelTaskRepository taskRepository;

    @Autowired
    private HotelTaskOperateRecordRepository taskOperateRecordRepository;

    /**
     * 获取工单列表
     */
    public Map<String, Object> getTaskList(Integer userId, TaskListRequest request) {
        return null;
    }

    /**
     * 获取工单详情
     */
    public Map<String, Object> getTaskDetail(Integer userId, TaskDetailRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return null; // 工单未找到
        }
        
        HotelTask task = taskOpt.get();
        Map<String, Object> taskDetail = new HashMap<>();
        taskDetail.put("taskId", task.getId());
        taskDetail.put("description", task.getDescription());
        taskDetail.put("roomId", task.getRoomId());
        taskDetail.put("roomName", task.getRoomName());
        taskDetail.put("guestId", task.getGuestId());
        taskDetail.put("guestName", task.getGuestName());
        taskDetail.put("deptId", task.getDeptId());
        taskDetail.put("deptName", task.getDeptName());
        taskDetail.put("creatorUserId", task.getCreatorUserId());
        taskDetail.put("creatorName", task.getCreatorName());
        taskDetail.put("execurorUserId", task.getExecutorUserId());
        taskDetail.put("executorName", task.getExecutorName());
        taskDetail.put("conversationId", task.getConversationId());
        taskDetail.put("conversationName", task.getConversationName());
        taskDetail.put("deadlineTime", task.getDeadlineTime());
        taskDetail.put("completeTime", task.getCompleteTime());
        taskDetail.put("priority", task.getPriority());
        taskDetail.put("taskStatus", task.getTaskStatus());
        taskDetail.put("taskStatusDisplayName", getTaskStatusDisplayName(task.getTaskStatus()));
        taskDetail.put("createTime", task.getCreateTime());
        taskDetail.put("updateTime", task.getUpdateTime());
        
        Map<String, Object> result = new HashMap<>();
        result.put("data", taskDetail);
        return result;
    }

    /**
     * 创建工单
     */
    public String createTask(Integer userId, TaskCreateRequest request) {
        HotelTask task = new HotelTask();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setRoomId(request.getRoomId());
        task.setGuestId(request.getGuestId());
        task.setDeptId(Integer.parseInt(request.getDeptId()));
        // 从其他服务或存储库获取部门名称
        task.setDeptName("部门 " + request.getDeptId()); // 占位符
        task.setCreatorUserId(userId);
        // 从用户服务获取创建者名称
        task.setCreatorName("用户 " + userId); // 占位符
        task.setConversationId(request.getConversationId());
        // 如果需要，获取会话名称
        if (request.getConversationId() != null) {
            task.setConversationName("会话 " + request.getConversationId()); // 占位符
        }
        task.setDeadlineTime(request.getDeadlineTime());
        task.setPriority(request.getPriority());
        task.setTaskStatus("PENDING"); // 初始状态
        task.setCreateTime((int)(System.currentTimeMillis() / 1000));
        task.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, null, "PENDING", "工单创建成功");
        
        return "success";
    }

    /**
     * 更新工单信息
     */
    public String updateTask(Integer userId, TaskUpdateRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return "工单未找到";
        }
        
        HotelTask task = taskOpt.get();
        
        // 检查用户是否有权限更新此工单
        if (!task.getCreatorUserId().equals(userId) && 
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId))) {
            return "无权更新工单";
        }
        
        // 更新字段
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeptId(request.getDeptId());
        // 从其他服务或存储库获取部门名称
        task.setDeptName("部门 " + request.getDeptId()); // 占位符
        task.setDeadlineTime(request.getDeadlineTime());
        task.setPriority(request.getPriority());
        task.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, null, null, "工单信息更新");
        
        return "success";
    }

    /**
     * 删除工单
     */
    public String deleteTask(String userId, TaskDeleteRequest request) {
        try {
            Integer taskId = Integer.parseInt(request.getTaskId());
            Optional<HotelTask> taskOpt = taskRepository.findById(taskId);
            
            if (!taskOpt.isPresent()) {
                return "工单未找到";
            }
            
            HotelTask task = taskOpt.get();
            
            // 检查用户是否有权限删除此工单
            if (!task.getCreatorUserId().toString().equals(userId)) {
                return "只有工单创建者可以删除工单";
            }
            
            taskRepository.deleteById(taskId);
            
            // 创建工单操作记录
            recordTaskOperation(taskId, Integer.parseInt(userId), null, null, "工单已删除");
            
            return "success";
        } catch (NumberFormatException e) {
            return "工单ID格式无效";
        }
    }

    /**
     * 添加执行人
     */
    public String addExecutor(String userId, TaskAddExecutorRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return "工单未找到";
        }
        
        HotelTask task = taskOpt.get();
        
        // 检查用户是否有权限添加执行人
        if (!task.getCreatorUserId().toString().equals(userId)) {
            return "只有工单创建者可以添加执行人";
        }
        
        // 检查工单是否已有执行人
        if (task.getExecutorUserId() != null) {
            return "工单已有执行人";
        }
        
        // 更新执行人
        task.setExecutorUserId(request.getExecutorUserId());
        // 从用户服务获取执行人名称
        task.setExecutorName("用户 " + request.getExecutorUserId()); // 占位符
        task.setTaskStatus("IN_PROGRESS"); // 更新状态
        task.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), Integer.parseInt(userId), 
                            null, "IN_PROGRESS", 
                            "已添加执行人: " + request.getExecutorUserId());
        
        return "success";
    }

    /**
     * 转移执行人
     */
    public String transferExecutor(Integer userId, TaskTransferExecutorRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return "工单未找到";
        }
        
        HotelTask task = taskOpt.get();
        
        // 检查用户是否有权限转移执行人
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId))) {
            return "只有工单创建者或当前执行人可以转移执行人";
        }
        
        Integer oldExecutorUserId = task.getExecutorUserId();
        
        // 更新执行人
        task.setExecutorUserId(request.getNewExecutorUserId());
        // 从用户服务获取执行人名称
        task.setExecutorName("用户 " + request.getNewExecutorUserId()); // 占位符
        task.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, 
                            oldExecutorUserId, null, 
                            "执行人从 " + oldExecutorUserId + " 转移到 " + request.getNewExecutorUserId());
        
        return "success";
    }

    /**
     * 变更工单状态
     */
    public String changeStatus(Integer userId, TaskChangeStatusRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return "工单未找到";
        }
        
        HotelTask task = taskOpt.get();
        
        // 检查用户是否有权限更改状态
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId)) &&
            !isUserDepartmentLeader(userId, task.getDeptId())) {
            return "只有工单创建者、执行人或部门领导可以更改状态";
        }
        
        String oldStatus = task.getTaskStatus();
        String newStatus = request.getNewTaskStatus();
        
        // 更新状态
        task.setTaskStatus(newStatus);
        task.setUpdateTime((int)(System.currentTimeMillis() / 1000));
        
        // 如果状态更改为COMPLETED，则更新完成时间
        if ("COMPLETED".equals(newStatus)) {
            task.setCompleteTime((int)(System.currentTimeMillis() / 1000));
        }
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, 
                            null, newStatus, 
                            "状态从 " + oldStatus + " 变更为 " + newStatus);
        
        return "success";
    }

    /**
     * 发送工单催办
     */
    public String sendReminder(Integer userId, TaskReminderRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return "工单未找到";
        }
        
        HotelTask task = taskOpt.get();
        
        // 记录催办操作
        recordTaskOperation(task.getId(), userId, 
                            task.getExecutorUserId(), null, 
                            "已发送催办");
        
        // 这里通常会向执行人发送通知
        // 这可能涉及通知服务、电子邮件服务或其他机制
        
        return "success";
    }

    /**
     * 获取工单SLA看板数据
     */
    public Map<String, Object> getTaskSLA(Integer userId) {
        // 计算按时完成率
        int totalCompleted = taskRepository.countByTaskStatus("COMPLETED");
        int onTimeCompleted = taskRepository.countByTaskStatusAndCompleteBeforeDeadline("COMPLETED");
        double onTimeCompletionRate = totalCompleted > 0 
            ? (double) onTimeCompleted / totalCompleted * 100 
            : 0;
            
        // 计算平均响应时间（分钟）
        int averageResponseTime = taskRepository.getAverageResponseTimeInMinutes();
        
        // 获取逾期工单数量
        int overdueTaskCount = taskRepository.countOverdueTasks();
        
        // 获取今日完成数量
        int todayStart = getTodayStartTimestamp();
        int todayCompletedCount = taskRepository.countByTaskStatusAndCompleteTimeGreaterThan("COMPLETED", todayStart);
        
        // 获取逾期工单列表
        List<HotelTask> overdueTasks = taskRepository.findOverdueTasks();
        List<Map<String, Object>> overdueList = overdueTasks.stream()
            .map(task -> {
                Map<String, Object> taskMap = new HashMap<>();
                taskMap.put("taskId", task.getId());
                taskMap.put("title", task.getTitle());
                taskMap.put("roomName", task.getRoomName());
                taskMap.put("deadlineTime", task.getDeadlineTime());
                return taskMap;
            })
            .collect(Collectors.toList());
        
        // 构建响应
        Map<String, Object> result = new HashMap<>();
        result.put("onTimeCompletionRate", (int) onTimeCompletionRate);
        result.put("averageResponseTime", averageResponseTime);
        result.put("overdueTaskCount", overdueTaskCount);
        result.put("todayCompletedCount", String.valueOf(todayCompletedCount));
        result.put("overdueList", overdueList);
        
        return result;
    }
    
    /**
     * 记录工单操作
     */
    private void recordTaskOperation(Integer taskId, Integer operatorUserId, 
                                    Integer executorUserId, String newStatus, 
                                    String operateDescription) {
        HotelTaskOperateRecord record = new HotelTaskOperateRecord();
        record.setTaskId(taskId);
        record.setOperatorUserId(operatorUserId);
        record.setExecutorUserId(executorUserId);
        record.setNewStatus(newStatus);
        record.setOperateDescription(operateDescription);
        record.setOperateTime((int)(System.currentTimeMillis() / 1000));
        
        taskOperateRecordRepository.save(record);
    }
    
    /**
     * 获取工单状态显示名称
     */
    private String getTaskStatusDisplayName(String status) {
        switch (status) {
            case "PENDING":
                return "待处理";
            case "IN_PROGRESS":
                return "进行中";
            case "PENDING_CONFIRMATION":
                return "待确认";
            case "COMPLETED":
                return "已完成";
            default:
                return status;
        }
    }
    
    /**
     * 检查用户是否是部门领导
     */
    private boolean isUserDepartmentLeader(Integer userId, Integer deptId) {
        // 这通常涉及检查部门或角色服务
        // 目前，我们返回一个占位符
        return false; // 占位符
    }
    
    /**
     * 获取今天开始的时间戳
     */
    private int getTodayStartTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (int)(calendar.getTimeInMillis() / 1000);
    }
}

package com.example.hoteltask.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hoteltask.dao.entity.HotelDepartment;
import com.example.hoteltask.dao.entity.HotelGuest;
import com.example.hoteltask.dao.entity.HotelRoom;
import com.example.hoteltask.dao.entity.HotelTask;
import com.example.hoteltask.dao.entity.HotelTaskOperateRecord;
import com.example.hoteltask.dao.entity.HotelUser;
import com.example.hoteltask.dao.repository.HotelDepartmentRepository;
import com.example.hoteltask.dao.repository.HotelGuestRepository;
import com.example.hoteltask.dao.repository.HotelRoomRepository;
import com.example.hoteltask.dao.repository.HotelTaskOperateRecordRepository;
import com.example.hoteltask.dao.repository.HotelTaskRepository;
import com.example.hoteltask.dao.repository.HotelUserRepository;
import com.example.hoteltask.enums.TaskOperateTypeEnum;
import com.example.hoteltask.enums.TaskPriorityEnum;
import com.example.hoteltask.enums.TaskStatusEnum;
import com.example.hoteltask.model.bo.TaskDetailBO;
import com.example.hoteltask.model.request.TaskAddExecutorRequest;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.model.request.TaskClaimRequest;
import com.example.hoteltask.model.request.TaskCreateRequest;
import com.example.hoteltask.model.request.TaskDeleteRequest;
import com.example.hoteltask.model.request.TaskDetailRequest;
import com.example.hoteltask.model.request.TaskListRequest;
import com.example.hoteltask.model.request.TaskReminderRequest;
import com.example.hoteltask.model.request.TaskTransferExecutorRequest;
import com.example.hoteltask.model.request.TaskUpdateRequest;
import com.example.hoteltask.model.response.ApiResponse;
import com.example.hoteltask.service.TaskService;

import jakarta.annotation.Resource;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private HotelTaskRepository taskRepository;

    @Resource
    private HotelTaskOperateRecordRepository taskOperateRecordRepository;

    @Resource
    private HotelRoomRepository hotelRoomRepository;

    @Resource
    private HotelGuestRepository hotelGuestRepository;

    @Resource
    private HotelDepartmentRepository hotelDepartmentRepository;

    @Resource
    private HotelUserRepository hotelUserRepository;

    /**
     * 获取工单列表
     */
    @Override
    public ResponseEntity<?> getTaskList(Long userId, TaskListRequest request) {
        // 这里需要实现工单列表逻辑
        Map<String, Object> result = new HashMap<>();
        // ... 假设这里有获取工单列表的逻辑
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 获取工单详情
     */
    @Override
    public ResponseEntity<?> getTaskDetail(Long userId, TaskDetailRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }

        HotelRoom hotelRoom = hotelRoomRepository.findById(task.getRoomId()).orElse(null);

        HotelGuest hotelGuest = hotelGuestRepository.findById(task.getGuestId()).orElse(null);

        HotelDepartment hotelDepartment = hotelDepartmentRepository.findById(task.getDeptId()).orElse(null);

        HotelUser creatorUser = hotelUserRepository.findById(task.getCreatorUserId()).orElse(null);
        HotelUser executorUser = hotelUserRepository.findById(task.getExecutorUserId()).orElse(null);
        
        TaskDetailBO taskDetail = new TaskDetailBO();
        taskDetail.setTaskId(task.getId());
        taskDetail.setTitle(task.getTitle());
        taskDetail.setDescription(task.getDescription());
        taskDetail.setRoomId(task.getRoomId());
        taskDetail.setRoomName(hotelRoom == null ? "" : hotelRoom.getName());
        taskDetail.setGuestId(task.getGuestId());
        taskDetail.setGuestName(hotelGuest == null ? "" : hotelGuest.getGuestName());
        taskDetail.setDeptId(task.getDeptId());
        taskDetail.setDeptName(hotelDepartment == null ? "" : hotelDepartment.getName());
        taskDetail.setCreatorUserId(task.getCreatorUserId());
        taskDetail.setCreatorName(creatorUser == null ? "" : creatorUser.getDisplayName());
        taskDetail.setExecutorUserId(task.getExecutorUserId());
        taskDetail.setExecutorName(executorUser == null ? "" : executorUser.getDisplayName());
        taskDetail.setConversationId(task.getConversationId());
        // todo 需要从会话服务获取
        taskDetail.setConversationName("");
        taskDetail.setDeadlineTime(task.getDeadlineTime());
        taskDetail.setCompleteTime(task.getCompleteTime());
        taskDetail.setPriority(taskDetail.getPriority());
        taskDetail.setPriorityDisplayName(TaskPriorityEnum.getByCode(task.getPriority()).getDisplayName());
        taskDetail.setTaskStatus(task.getTaskStatus());
        taskDetail.setTaskStatusDisplayName(TaskStatusEnum.getByCode(task.getTaskStatus()).getDisplayName());
        taskDetail.setCreateTime(task.getCreateTime());
        taskDetail.setUpdateTime(task.getUpdateTime());
        
        return ResponseEntity.ok(ApiResponse.success(taskDetail));
    }

    /**
     * 创建工单
     */
    @Override
    public ResponseEntity<?> createTask(Long userId, TaskCreateRequest request) {
        HotelTask task = new HotelTask();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setRoomId(request.getRoomId());
        task.setGuestId(request.getGuestId());
        task.setDeptId(request.getDeptId());
        task.setCreatorUserId(userId);
        task.setConversationId(request.getConversationId());
        task.setDeadlineTime(request.getDeadlineTime());
        task.setPriority(request.getPriority());
        task.setTaskStatus(TaskStatusEnum.PENDING.getCode()); // 初始状态为待处理
        task.setCreateTime(new Timestamp(System.currentTimeMillis()));
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.CREATE);
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 更新工单信息
     */
    @Override
    public ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        
        // 检查用户是否有权限更新此工单
        if (!task.getCreatorUserId().equals(userId) && 
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId))) {
            return ResponseEntity.ok(ApiResponse.error(403, "无权更新工单", "Forbidden"));
        }
        
        // 更新字段
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeptId(request.getDeptId());
        task.setDeadlineTime(new Timestamp(request.getDeadlineTime()));
        task.setPriority(request.getPriority());
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.UPDATE);
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 删除工单
     */
    @Override
    public ResponseEntity<?> deleteTask(Long userId, TaskDeleteRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);

        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        
        // 检查用户是否有权限删除此工单
        if (!task.getCreatorUserId().equals(userId)) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者可以删除工单", "Forbidden"));
        }
        
        taskRepository.deleteById(request.getTaskId());
        
        // 创建工单操作记录
        recordTaskOperation(request.getTaskId(), userId, TaskOperateTypeEnum.DELETE);
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    @Override
    public ResponseEntity<?> claimTask(Long userId, TaskClaimRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);

        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }

        task.setExecutorUserId(userId);
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        taskRepository.save(task);

        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.CLAIM);

        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 添加执行人
     */
    @Override
    public ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        
        // 检查用户是否有权限添加执行人
        if (!task.getCreatorUserId().equals(userId)) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者可以添加执行人", "Forbidden"));
        }
        
        // 检查工单是否已有执行人
        if (task.getExecutorUserId() != null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单已有执行人", "Bad Request"));
        }
        
        // 更新执行人
        task.setExecutorUserId(request.getExecutorUserId());
        task.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.CLAIM);
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 转移执行人
     */
    @Override
    public ResponseEntity<?> transferExecutor(Long userId, TaskTransferExecutorRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }

        // 检查用户是否有权限转移执行人
        // 工单创建人/执行人/部门领导可以转移执行人
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId)) &&
                isUserDepartmentLeader(userId, task.getDeptId())) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者或当前执行人或部门领导可以转移执行人", "Forbidden"));
        }
        
        // 更新执行人
        task.setExecutorUserId(request.getNewExecutorUserId());
        task.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        
        taskRepository.save(task);
        
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.TRANSFER);
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 变更工单状态
     */
    @Override
    public ResponseEntity<?> changeStatus(Long userId, TaskChangeStatusRequest request) {
        Optional<HotelTask> taskOpt = taskRepository.findById(request.getTaskId());
        
        if (!taskOpt.isPresent()) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        
        HotelTask task = taskOpt.get();
        
        // 检查用户是否有权限更改状态
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId)) &&
                isUserDepartmentLeader(userId, task.getDeptId())) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者、执行人或部门领导可以更改状态", "Forbidden"));
        }
        
        // 更新状态
        task.setTaskStatus(request.getNewTaskStatus());
        task.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.IN_PROGRESS.getCode())) {
            task.setStartProcessTime(new Timestamp(System.currentTimeMillis()));
            recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.START_PROCESS);
        } else if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.PENDING_CONFIRMATION.getCode())) {
            task.setCompleteTime(new Timestamp(System.currentTimeMillis()));
            recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.PENDING_CONFIRMATION);
        } else if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.COMPLETED.getCode())) {
            recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.COMPLETE);
        }

        taskRepository.save(task);

        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 发送工单催办
     */
    @Override
    public ResponseEntity<?> sendReminder(Long userId, TaskReminderRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "工单未找到", "Not Found"));
        }
        
        // 记录催办操作
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.REMIND);
        
        // 这里通常会向执行人发送通知
        // 这可能涉及通知服务、电子邮件服务或其他机制
        
        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 获取工单SLA看板数据
     */
    @Override
    public ResponseEntity<?> getTaskSLA(Long userId) {
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
                // 这里需要从其他服务获取roomName
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
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    /**
     * 记录工单操作
     */
    private void recordTaskOperation(Long taskId, Long operatorUserId, TaskOperateTypeEnum operateType) {
        HotelTaskOperateRecord record = new HotelTaskOperateRecord();
        record.setTaskId(taskId);
        record.setOperatorUserId(operatorUserId);
        record.setOperateType(operateType.getCode());
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        taskOperateRecordRepository.save(record);
    }
    
    /**
     * 检查用户是否是部门领导
     */
    private boolean isUserDepartmentLeader(Long userId, Long deptId) {
        HotelDepartment hotelDepartment = hotelDepartmentRepository.findById(deptId).orElse(null);

        if (hotelDepartment == null) {
            return true;
        }

        return !hotelDepartment.getLeaderUserId().equals(userId);
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

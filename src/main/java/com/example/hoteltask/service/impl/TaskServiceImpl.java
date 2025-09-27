package com.example.hoteltask.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hoteltask.constants.CommonConstant;
import com.example.hoteltask.dao.entity.HotelDepartment;
import com.example.hoteltask.dao.entity.HotelGuest;
import com.example.hoteltask.dao.entity.HotelRoleMenu;
import com.example.hoteltask.dao.entity.HotelRoom;
import com.example.hoteltask.dao.entity.HotelTask;
import com.example.hoteltask.dao.entity.HotelTaskOperateRecord;
import com.example.hoteltask.dao.entity.HotelUser;
import com.example.hoteltask.dao.entity.HotelUserRole;
import com.example.hoteltask.dao.repository.HotelDepartmentRepository;
import com.example.hoteltask.dao.repository.HotelGuestRepository;
import com.example.hoteltask.dao.repository.HotelRoleMenuRepository;
import com.example.hoteltask.dao.repository.HotelRoomRepository;
import com.example.hoteltask.dao.repository.HotelTaskOperateRecordRepository;
import com.example.hoteltask.dao.repository.HotelTaskRepository;
import com.example.hoteltask.dao.repository.HotelUserRepository;
import com.example.hoteltask.dao.repository.HotelUserRoleRepository;
import com.example.hoteltask.enums.TaskOperateTypeEnum;
import com.example.hoteltask.enums.TaskPriorityEnum;
import com.example.hoteltask.enums.TaskStatusEnum;
import com.example.hoteltask.model.bo.TaskDetailBO;
import com.example.hoteltask.model.bo.TaskListColumnBO;
import com.example.hoteltask.model.bo.TaskListItemBO;
import com.example.hoteltask.model.request.TaskAddExecutorRequest;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.model.request.TaskClaimRequest;
import com.example.hoteltask.model.request.TaskColumnRequest;
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
import org.springframework.util.StringUtils;

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

    @Resource
    private HotelUserRoleRepository hotelUserRoleRepository;

    @Resource
    private HotelRoleMenuRepository hotelRoleMenuRepository;

    private static final int TASK_LIST_PAGE = 20;

    private static final long ALL_DEPARTMENT_ID = -1L;

    /**
     * 获取工单列表
     */
    @Override
    public ResponseEntity<?> getTaskList(Long userId, TaskListRequest request) {
        // 检查请求用户为部门领导或拥有"可查看全部工单"权限时，userVisibleAll=true
        boolean userVisibleAll = isUserVisibleAll(userId, request.getDepartmentId());

        // 如果请求的是全部部门，则不进行部门过滤
        Long deptIdFilter;
        if (request.getDepartmentId() == null ||
                request.getDepartmentId() == ALL_DEPARTMENT_ID) {
            deptIdFilter = null;
        } else {
            deptIdFilter = request.getDepartmentId();
        }
        
        // 请求 inProgressList 类型的工单列表时,
        // userVisibleAll=true，可以看到所有工单;
        // userVisibleAll=false，只能看到执行人为自己的工单
        List<TaskStatusEnum> inProgressList = List.of(
                TaskStatusEnum.IN_PROGRESS,
                TaskStatusEnum.REVIEW,
                TaskStatusEnum.COMPLETED);

        List<TaskListColumnBO> result = new ArrayList<>();
        List<HotelTask> allHotelTasks = new ArrayList<>();
        for (TaskColumnRequest columnRequest : request.getRequireTaskColumnList()) {
            TaskListColumnBO taskListColumnBO = new TaskListColumnBO();
            TaskStatusEnum taskStatus = TaskStatusEnum.getByName(columnRequest.getTaskStatus());

            List<HotelTask> hotelTasks;
            int totalTaskCount;
            Timestamp lastTaskCreateTime = columnRequest.getLastTaskCreateTime() == null ? null : new Timestamp(columnRequest.getLastTaskCreateTime());
            if (inProgressList.contains(taskStatus)) {
                hotelTasks = taskRepository.findByTaskStatusAndFilters(
                        taskStatus.getName(),
                        deptIdFilter,
                        userVisibleAll ? null : userId,
                        request.getPriority(),
                        columnRequest.getLastTaskId(),
                        lastTaskCreateTime,
                        TASK_LIST_PAGE);
                        
                // 获取该状态下的总任务数
                totalTaskCount = taskRepository.countByTaskStatusAndFilters(
                        taskStatus.getName(),
                        deptIdFilter,
                        userVisibleAll ? null : userId,
                        request.getPriority());
            } else {
                hotelTasks = taskRepository.findByTaskStatusAndFilters(
                        taskStatus.getName(),
                        deptIdFilter,
                        null,
                        request.getPriority(),
                        columnRequest.getLastTaskId(),
                        lastTaskCreateTime,
                        TASK_LIST_PAGE);
                        
                // 获取该状态下的总任务数
                totalTaskCount = taskRepository.countByTaskStatusAndFilters(
                        taskStatus.getName(),
                        deptIdFilter,
                        null,
                        request.getPriority());
            }
            allHotelTasks.addAll(hotelTasks);
            List<TaskListItemBO> tasks = hotelTasks.stream().map(hotelTask -> {
                TaskListItemBO taskListItemBO = new TaskListItemBO();
                taskListItemBO.setTaskId(hotelTask.getId());
                taskListItemBO.setTitle(hotelTask.getTitle());
                taskListItemBO.setDescription(hotelTask.getDescription());
                taskListItemBO.setRoomId(hotelTask.getRoomId());
                taskListItemBO.setGuestId(hotelTask.getGuestId());
                taskListItemBO.setDeptId(hotelTask.getDeptId());
                taskListItemBO.setTaskStatus(hotelTask.getTaskStatus()); 
                taskListItemBO.setTaskStatusDisplayName(TaskStatusEnum.getByName(hotelTask.getTaskStatus()).getDisplayName());
                taskListItemBO.setPriority(hotelTask.getPriority());
                taskListItemBO.setPriorityDisplayName(TaskPriorityEnum.getByCode(hotelTask.getPriority()).getDisplayName());
                taskListItemBO.setCreateTime(hotelTask.getCreateTime().getTime());
                taskListItemBO.setUpdateTime(hotelTask.getUpdateTime().getTime());
                taskListItemBO.setDeadlineTime(hotelTask.getDeadlineTime() == null ? null : hotelTask.getDeadlineTime().getTime());
                taskListItemBO.setCompleteTime(hotelTask.getCompleteTime() == null ? null : hotelTask.getCompleteTime().getTime());
                return taskListItemBO;
            }).toList();
            taskListColumnBO.setTasks(tasks);
            taskListColumnBO.setTaskCount(totalTaskCount); // 使用总任务数而不是当前页的任务数
            taskListColumnBO.setTaskStatus(taskStatus.getName());
            taskListColumnBO.setTaskStatusDisplayName(taskStatus.getDisplayName());
            result.add(taskListColumnBO);
        }

        // 补全工单中的roomName,guestName,deptName信息
        fillTaskListColumnBO(result, allHotelTasks);
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    private boolean isUserVisibleAll(Long userId, Long departmentId) {
        HotelUser hotelUser = hotelUserRepository.findById(userId).orElse(null);
        if (hotelUser != null && BooleanUtils.isTrue(hotelUser.getSuperAdmin())) {
            return true;
        }
        if (departmentId != null) {
            return isUserDepartmentLeader(userId, departmentId);
        }
        else {
            List<HotelUserRole> hotelUserRoles = hotelUserRoleRepository.findByUserId(userId);
            List<Long> roleIdList = hotelUserRoles.stream().map(HotelUserRole::getRoleId).distinct().toList();
            if (!roleIdList.isEmpty()) {
                List<HotelRoleMenu> byRoleIdsAndMenuId = hotelRoleMenuRepository.findByRoleIdsAndMenuId(roleIdList, CommonConstant.TASK_LIST_VISIBLE_ALL_MENU);
                return !byRoleIdsAndMenuId.isEmpty();
            }
        }
        return false;
    }

    private void fillTaskListColumnBO(List<TaskListColumnBO> taskListColumnBOList, List<HotelTask> allHotelTasks) {
        // 获取roomId批量查询room信息，获取guestId批量查询guest信息，获取deptId批量查询dept信息
        List<Long> roomIdList = allHotelTasks.stream().map(HotelTask::getRoomId).filter(Objects::nonNull).distinct().toList();
        List<Long> guestIdList = allHotelTasks.stream().map(HotelTask::getGuestId).filter(Objects::nonNull).distinct().toList();
        List<Long> deptIdList = allHotelTasks.stream().map(HotelTask::getDeptId).filter(Objects::nonNull).distinct().toList();
        List<HotelRoom> hotelRooms = roomIdList.isEmpty() ? new ArrayList<>() : hotelRoomRepository.findByIdIn(roomIdList);
        List<HotelGuest> hotelGuests = guestIdList.isEmpty() ? new ArrayList<>() : hotelGuestRepository.findByIdIn(guestIdList);
        List<HotelDepartment> hotelDepartments = deptIdList.isEmpty() ? new ArrayList<>() : hotelDepartmentRepository.findByIdIn(deptIdList);
        Map<Long, HotelRoom> roomIdToRoomMap = hotelRooms.stream().collect(Collectors.toMap(HotelRoom::getId, Function.identity()));
        Map<Long, HotelGuest> guestIdToGuestMap = hotelGuests.stream().collect(Collectors.toMap(HotelGuest::getId, Function.identity()));
        Map<Long, HotelDepartment> deptIdToDeptMap = hotelDepartments.stream().collect(Collectors.toMap(HotelDepartment::getId, Function.identity()));

        for (TaskListColumnBO taskListColumnBO : taskListColumnBOList) {
            for (TaskListItemBO taskListItemBO : taskListColumnBO.getTasks()) {
                // 非空判断
                if (taskListItemBO.getRoomId() != null) {
                    HotelRoom hotelRoom = roomIdToRoomMap.get(taskListItemBO.getRoomId());
                    taskListItemBO.setRoomName(hotelRoom == null ? "" : hotelRoom.getName());
                }
                if (taskListItemBO.getGuestId() != null) {
                    HotelGuest hotelGuest = guestIdToGuestMap.get(taskListItemBO.getGuestId());
                    taskListItemBO.setGuestName(hotelGuest == null ? "" : hotelGuest.getGuestName());
                }
                if (taskListItemBO.getDeptId() != null) {
                    HotelDepartment hotelDepartment = deptIdToDeptMap.get(taskListItemBO.getDeptId());
                    taskListItemBO.setDeptName(hotelDepartment == null ? "" : hotelDepartment.getName());
                }
            }
        }
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
        // 非空判断
        HotelRoom hotelRoom = task.getRoomId() == null ? null : hotelRoomRepository.findById(task.getRoomId()).orElse(null);
        HotelGuest hotelGuest = task.getGuestId() == null ? null : hotelGuestRepository.findById(task.getGuestId()).orElse(null);
        HotelDepartment hotelDepartment = task.getDeptId() == null ? null : hotelDepartmentRepository.findById(task.getDeptId()).orElse(null);

        HotelUser creatorUser = task.getCreatorUserId() == null ? null : hotelUserRepository.findById(task.getCreatorUserId()).orElse(null);
        HotelUser executorUser = task.getExecutorUserId() == null ? null : hotelUserRepository.findById(task.getExecutorUserId()).orElse(null);
        
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
        if (task.getDeadlineTime() != null) {
            taskDetail.setDeadlineTime(task.getDeadlineTime().getTime());
        }
        if (task.getCompleteTime() != null) {
            taskDetail.setCompleteTime(task.getCompleteTime().getTime());
        }
        taskDetail.setPriority(task.getPriority());
        taskDetail.setPriorityDisplayName(TaskPriorityEnum.getByCode(task.getPriority()).getDisplayName());
        taskDetail.setTaskStatus(task.getTaskStatus());
        taskDetail.setTaskStatusDisplayName(TaskStatusEnum.getByName(task.getTaskStatus()).getDisplayName());
        taskDetail.setCreateTime(task.getCreateTime().getTime());
        taskDetail.setUpdateTime(task.getUpdateTime().getTime());
        
        return ResponseEntity.ok(ApiResponse.success(taskDetail));
    }

    /**
     * 创建工单
     */
    @Override
    public ResponseEntity<?> createTask(Long userId, TaskCreateRequest request) {
        // 判断priority不合法，提前返回
        if (!verifyTaskPriority(request.getPriority())) {
            return ResponseEntity.ok(ApiResponse.error(400, "优先级不合法", "Bad Request"));
        }
        HotelTask task = new HotelTask();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setRoomId(request.getRoomId());
        task.setGuestId(request.getGuestId());
        task.setDeptId(request.getDeptId());
        task.setCreatorUserId(userId);
        task.setConversationId(request.getConversationId());
        if (request.getDeadlineTime() != null) {
            task.setDeadlineTime(new Timestamp(request.getDeadlineTime()));
        }
        task.setPriority(request.getPriority());
        task.setTaskStatus(TaskStatusEnum.PENDING.getName()); // 初始状态为待处理
        task.setCreateTime(new Timestamp(System.currentTimeMillis()));
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        taskRepository.save(task);
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.CREATE);

        return ResponseEntity.ok(ApiResponse.success(task.getId()));
    }

    /**
     * 更新工单信息
     */
    @Override
    public ResponseEntity<?> updateTask(Long userId, TaskUpdateRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
        }
        // 判断priority不合法，提前返回
        if (!verifyTaskPriority(request.getPriority())) {
            return ResponseEntity.ok(ApiResponse.error(400, "优先级不合法", "Bad Request"));
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
        if (request.getDeadlineTime() != null) {
            task.setDeadlineTime(new Timestamp(request.getDeadlineTime()));
        } else {
            task.setDeadlineTime(null);
        }
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
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
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
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
        }
        if (task.getExecutorUserId() != null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单已有执行人", "BadRequest"));
        }
        task.setExecutorUserId(userId);
        task.setTaskStatus(TaskStatusEnum.IN_PROGRESS.getName());
        task.setStartProcessTime(new Timestamp(System.currentTimeMillis()));
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        taskRepository.save(task);
        // 创建工单操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.CLAIM);
        // 工单开始执行操作记录
        recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.START_PROCESS);

        return ResponseEntity.ok(ApiResponse.success("success"));
    }

    /**
     * 添加执行人
     */
    @Override
    public ResponseEntity<?> addExecutor(Long userId, TaskAddExecutorRequest request) {
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
        }
        // 检查用户是否有权限添加执行人
        if (!task.getCreatorUserId().equals(userId) || !isUserDepartmentLeader(userId, task.getDeptId())) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者或部门领导可以添加执行人", "Forbidden"));
        }
        // 检查工单是否已有执行人
        if (task.getExecutorUserId() != null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单已有执行人", "Bad Request"));
        }
        // 更新执行人
        task.setExecutorUserId(request.getExecutorUserId());
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
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
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
        }
        // 检查用户是否有权限转移执行人
        // 工单创建人/执行人/部门领导可以转移执行人
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId)) &&
                !isUserDepartmentLeader(userId, task.getDeptId())) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者或当前执行人或部门领导可以转移执行人", "Forbidden"));
        }
        // 更新执行人
        task.setExecutorUserId(request.getNewExecutorUserId());
        task.setUpdateTime(new Timestamp(System.currentTimeMillis()));
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
        HotelTask task = taskRepository.findById(request.getTaskId()).orElse(null);
        if (task == null) {
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
        }
        // 判断status不合法，提前返回
        if (!verifyTaskStatus(request.getNewTaskStatus())) {
            return ResponseEntity.ok(ApiResponse.error(400, "状态不合法", "Bad Request"));
        }
        // 检查用户是否有权限更改状态
        if (!task.getCreatorUserId().equals(userId) &&
            (task.getExecutorUserId() == null || !task.getExecutorUserId().equals(userId)) &&
                !isUserDepartmentLeader(userId, task.getDeptId())) {
            return ResponseEntity.ok(ApiResponse.error(403, "只有工单创建者、执行人或部门领导可以更改状态", "Forbidden"));
        }
        // 更新状态
        task.setTaskStatus(request.getNewTaskStatus());
        task.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.IN_PROGRESS.getName())) {
            task.setStartProcessTime(new Timestamp(System.currentTimeMillis()));
            recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.START_PROCESS);
        } else if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.REVIEW.getName())) {
            task.setCompleteTime(new Timestamp(System.currentTimeMillis()));
            recordTaskOperation(task.getId(), userId, TaskOperateTypeEnum.REVIEW);
        } else if (Objects.equals(request.getNewTaskStatus(), TaskStatusEnum.COMPLETED.getName())) {
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
            return ResponseEntity.ok(ApiResponse.error(400, "工单未找到", "Bad Request"));
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
        return null;
    }
    
    /**
     * 获取工单总数
     */
    @Override
    public ResponseEntity<?> getTotalCount() {
        int total = (int) taskRepository.count();
        return ResponseEntity.ok(ApiResponse.success(total));
    }
    
    /**
     * 记录工单操作
     */
    private void recordTaskOperation(Long taskId, Long operatorUserId, TaskOperateTypeEnum operateType) {
        HotelTaskOperateRecord record = new HotelTaskOperateRecord();
        record.setTaskId(taskId);
        record.setOperatorUserId(operatorUserId);
        // 使用getCode方法
        record.setOperateType(operateType.getCode());
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        taskOperateRecordRepository.save(record);
    }
    
    /**
     * 检查用户是否是部门领导
     */
    private boolean isUserDepartmentLeader(Long userId, Long deptId) {
        if (deptId == null) {
            return false;
        }
        HotelDepartment hotelDepartment = hotelDepartmentRepository.findById(deptId).orElse(null);
        if (hotelDepartment == null) {
            return true;
        }
        return !hotelDepartment.getLeaderUserId().equals(userId);
    }

    private boolean verifyTaskPriority(String priority) {
        return TaskPriorityEnum.getByCode(priority) != null;
    }

    private boolean verifyTaskStatus(String status) {
        return TaskStatusEnum.getByName(status) != null;
    }
}

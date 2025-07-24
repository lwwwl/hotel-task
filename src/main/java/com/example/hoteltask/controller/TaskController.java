package com.example.hoteltask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltask.aop.annotation.RequireUserId;
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
import com.example.hoteltask.service.TaskService;
import com.example.hoteltask.utils.UserContext;

import jakarta.annotation.Resource;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(@RequestBody TaskListRequest request) {
        return taskService.getTaskList(UserContext.getUserId(), request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getTaskDetail(@RequestBody TaskDetailRequest request) {
        return taskService.getTaskDetail(UserContext.getUserId(), request);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest request) {
        return taskService.createTask(UserContext.getUserId(), request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(UserContext.getUserId(), request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestBody TaskDeleteRequest request) {
        return taskService.deleteTask(UserContext.getUserId(), request);
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claimTask(@RequestBody TaskClaimRequest request) {
        return taskService.claimTask(UserContext.getUserId(), request);
    }

    @PostMapping("/add-executor")
    public ResponseEntity<?> addExecutor(@RequestBody TaskAddExecutorRequest request) {
        return taskService.addExecutor(UserContext.getUserId(), request);
    }

    @PostMapping("/transfer-executor")
    public ResponseEntity<?> transferExecutor(@RequestBody TaskTransferExecutorRequest request) {
        return taskService.transferExecutor(UserContext.getUserId(), request);
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(@RequestBody TaskChangeStatusRequest request) {
        return taskService.changeStatus(UserContext.getUserId(), request);
    }

    @PostMapping("/reminder")
    public ResponseEntity<?> sendReminder(@RequestBody TaskReminderRequest request) {
        return taskService.sendReminder(UserContext.getUserId(), request);
    }

    @PostMapping("/")
    public ResponseEntity<?> getTaskSLA() {
        return taskService.getTaskSLA(UserContext.getUserId());
    }
} 
package com.example.hoteltask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

import jakarta.annotation.Resource;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskListRequest request) {
        return taskService.getTaskList(userId, request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getTaskDetail(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskDetailRequest request) {
        return taskService.getTaskDetail(userId, request);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskCreateRequest request) {
        return taskService.createTask(userId, request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskUpdateRequest request) {
        return taskService.updateTask(userId, request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskDeleteRequest request) {
        return taskService.deleteTask(userId, request);
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claimTask(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskClaimRequest request) {
        return taskService.claimTask(userId, request);
    }

    @PostMapping("/add-executor")
    public ResponseEntity<?> addExecutor(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskAddExecutorRequest request) {
        return taskService.addExecutor(userId, request);
    }

    @PostMapping("/transfer-executor")
    public ResponseEntity<?> transferExecutor(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskTransferExecutorRequest request) {
        return taskService.transferExecutor(userId, request);
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskChangeStatusRequest request) {
        return taskService.changeStatus(userId, request);
    }

    @PostMapping("/reminder")
    public ResponseEntity<?> sendReminder(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskReminderRequest request) {
        return taskService.sendReminder(userId, request);
    }

    @PostMapping("/")
    public ResponseEntity<?> getTaskSLA(
            @RequestHeader(value = "userId") Long userId) {
        return taskService.getTaskSLA(userId);
    }
} 
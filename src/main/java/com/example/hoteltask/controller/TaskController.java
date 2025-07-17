package com.example.hoteltask.controller;

import com.example.hoteltask.aop.annotation.RequireUserId;
import com.example.hoteltask.model.request.*;
import com.example.hoteltask.model.response.ApiResponse;
import com.example.hoteltask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/list")
    public ResponseEntity<?> getTaskList(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskListRequest request) {
        Map<String, Object> result = taskService.getTaskList(userId, request);
        return ResponseEntity.ok(ApiResponse.success(result.get("data")));
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getTaskDetail(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskDetailRequest request) {
        Map<String, Object> result = taskService.getTaskDetail(userId, request);
        if (result == null) {
            return ResponseEntity.ok(ApiResponse.error(404, "Task not found", "Not Found"));
        }
        return ResponseEntity.ok(ApiResponse.success(result.get("data")));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskCreateRequest request) {
        String result = taskService.createTask(userId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskUpdateRequest request) {
        String result = taskService.updateTask(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(
            @RequestHeader(value = "userId") String userId,
            @RequestBody TaskDeleteRequest request) {
        String result = taskService.deleteTask(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/add-executor")
    public ResponseEntity<?> addExecutor(
            @RequestHeader(value = "userId") String userId,
            @RequestBody TaskAddExecutorRequest request) {
        String result = taskService.addExecutor(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/transfer-executor")
    public ResponseEntity<?> transferExecutor(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskTransferExecutorRequest request) {
        String result = taskService.transferExecutor(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskChangeStatusRequest request) {
        String result = taskService.changeStatus(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/reminder")
    public ResponseEntity<?> sendReminder(
            @RequestHeader(value = "userId") Integer userId,
            @RequestBody TaskReminderRequest request) {
        String result = taskService.sendReminder(userId, request);
        if (!"success".equals(result)) {
            return ResponseEntity.ok(ApiResponse.error(400, result, "Bad Request"));
        }
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/")
    public ResponseEntity<?> getTaskSLA(
            @RequestHeader(value = "userId") Integer userId) {
        Map<String, Object> result = taskService.getTaskSLA(userId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
} 
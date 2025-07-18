package com.example.hoteltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltask.aop.annotation.RequireUserId;
import com.example.hoteltask.model.request.TaskChangeStatusRequest;
import com.example.hoteltask.service.TaskFlowService;

/**
 * 工单流程控制器，处理与Flowable流程相关的API
 */
@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/task-flow")
public class TaskFlowController {

    @Autowired
    private TaskFlowService taskFlowService;

    /**
     * 通过Flowable流程变更工单状态
     */
    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatusByFlow(
            @RequestHeader(value = "userId") Long userId,
            @RequestBody TaskChangeStatusRequest request) {
        return taskFlowService.changeStatusByFlowable(userId, request);
    }
} 
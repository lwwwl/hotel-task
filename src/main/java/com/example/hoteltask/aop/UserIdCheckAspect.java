package com.example.hoteltask.aop;

import com.example.hoteltask.model.response.ApiResponse;
import com.example.hoteltask.utils.UserContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserIdCheckAspect {

    @Around("@within(com.example.hoteltask.aop.annotation.RequireUserId) || @annotation(com.example.hoteltask.aop.annotation.RequireUserId)")
    public Object checkUserIdHeader(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String userIdStr = request.getHeader("X-User-Id");

            if (userIdStr == null || userIdStr.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), 
                                            "Missing required X-User-Id header",
                                            "Unauthorized"));
            }
            try {
                Long userId = Long.parseLong(userIdStr);
                UserContext.setUserId(userId);
            } catch (NumberFormatException e) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), 
                                            "Invalid X-User-Id format",
                                            "Bad Request"));
            }
            return joinPoint.proceed();
        } finally {
            UserContext.clear();
        }
    }
} 
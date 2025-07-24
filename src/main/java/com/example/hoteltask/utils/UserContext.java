package com.example.hoteltask.utils;

/**
 * User context utility for storing user information in ThreadLocal
 */
public class UserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    
    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }
    
    public static Long getUserId() {
        return USER_ID.get();
    }
    
    public static void clear() {
        USER_ID.remove();
    }
} 
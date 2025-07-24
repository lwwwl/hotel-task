package com.example.hoteltask.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelTask;

@Repository
public interface HotelTaskRepository extends JpaRepository<HotelTask, Long> {

    @Query(value = "SELECT t FROM HotelTask t WHERE t.taskStatus = :taskStatus " +
            "AND (:departmentId IS NULL OR t.deptId = :departmentId) " +
            "AND (:executorId IS NULL OR t.executorId = :executorId) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND ((:lastTaskId IS NULL AND :lastTaskCreateTime IS NULL) OR " +
            "     (t.id < :lastTaskId) OR " +
            "     (t.id = :lastTaskId AND t.createTime < :lastTaskCreateTime)) " +
            "ORDER BY t.id DESC, t.createTime DESC " +
            "LIMIT :limit")
    List<HotelTask> findByTaskStatusAndFilters(
            @Param("taskStatus") Integer taskStatus,
            @Param("departmentId") Long departmentId,
            @Param("executorId") Long executorId,
            @Param("priority") Integer priority,
            @Param("lastTaskId") Long lastTaskId,
            @Param("lastTaskCreateTime") Timestamp lastTaskCreateTime,
            @Param("limit") Integer limit);
            
    /**
     * 统计特定状态和过滤条件下的任务总数
     */
    @Query(value = "SELECT COUNT(t) FROM HotelTask t WHERE t.taskStatus = :taskStatus " +
            "AND (:departmentId IS NULL OR t.deptId = :departmentId) " +
            "AND (:executorId IS NULL OR t.executorId = :executorId) " +
            "AND (:priority IS NULL OR t.priority = :priority)")
    int countByTaskStatusAndFilters(
            @Param("taskStatus") Integer taskStatus,
            @Param("departmentId") Long departmentId,
            @Param("executorId") Long executorId,
            @Param("priority") Integer priority);

    @Query(value = "SELECT COUNT(t) FROM HotelTask t WHERE t.taskStatus = :status")
    int countByTaskStatus(@Param("status") String status);
    
    @Query(value = "SELECT COUNT(t) FROM HotelTask t WHERE t.taskStatus = :status " +
            "AND t.completeTime <= t.deadlineTime")
    int countByTaskStatusAndCompleteBeforeDeadline(@Param("status") String status);
    
    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE, t.createTime, " +
            "COALESCE(t.completeTime, UNIX_TIMESTAMP()))) FROM HotelTask t " +
            "WHERE t.taskStatus IN ('IN_PROGRESS', 'COMPLETED')")
    int getAverageResponseTimeInMinutes();
    
    @Query(value = "SELECT COUNT(t) FROM HotelTask t " +
            "WHERE t.deadlineTime < UNIX_TIMESTAMP() " +
            "AND t.taskStatus NOT IN ('COMPLETED')")
    int countOverdueTasks();
    
    @Query(value = "SELECT COUNT(t) FROM HotelTask t " +
            "WHERE t.taskStatus = :status AND t.completeTime >= :timeThreshold")
    int countByTaskStatusAndCompleteTimeGreaterThan(
            @Param("status") String status,
            @Param("timeThreshold") int timeThreshold);
    
    @Query(value = "SELECT t FROM HotelTask t " +
            "WHERE t.deadlineTime < UNIX_TIMESTAMP() " +
            "AND t.taskStatus NOT IN ('COMPLETED') " +
            "ORDER BY t.deadlineTime ASC")
    List<HotelTask> findOverdueTasks();
} 
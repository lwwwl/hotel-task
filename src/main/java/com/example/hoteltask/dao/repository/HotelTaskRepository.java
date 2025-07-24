package com.example.hoteltask.dao.repository;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelTask;

@Repository
public interface HotelTaskRepository extends JpaRepository<HotelTask, Long> {


    @Query(value = "SELECT * FROM hotel_tasks t WHERE t.task_status = :taskStatus " +
            "AND (:departmentId IS NULL OR t.dept_id = :departmentId) " +
            "AND (:executorId IS NULL OR t.executor_user_id = :executorId) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND ((CAST(:lastTaskId AS BIGINT) IS NULL AND CAST(:lastTaskCreateTime AS TIMESTAMP) IS NULL) OR " +
            "     (t.id < :lastTaskId) OR " +
            "     (t.id = :lastTaskId AND t.create_time < :lastTaskCreateTime)) " +
            "ORDER BY t.id DESC, t.create_time DESC " +
            "LIMIT CAST(:limit AS INTEGER)",
            nativeQuery = true)
    List<HotelTask> findByTaskStatusAndFilters(
            @Param("taskStatus") String taskStatus,
            @Param("departmentId") Long departmentId,
            @Param("executorId") Long executorId,
            @Param("priority") String priority,
            @Param("lastTaskId") Long lastTaskId,
            @Param("lastTaskCreateTime") Timestamp lastTaskCreateTime,
            @Param("limit") Integer limit);

    /**
     * 统计特定状态和过滤条件下的任务总数
     */
    @Query(value = "SELECT COUNT(*) FROM hotel_tasks t WHERE t.task_status = :taskStatus " +
            "AND (:departmentId IS NULL OR t.dept_id = :departmentId) " +
            "AND (:executorId IS NULL OR t.executor_user_id = :executorId) " +
            "AND (:priority IS NULL OR t.priority = :priority)",
            nativeQuery = true)
    int countByTaskStatusAndFilters(
            @Param("taskStatus") String taskStatus,
            @Param("departmentId") Long departmentId,
            @Param("executorId") Long executorId,
            @Param("priority") String priority);
} 
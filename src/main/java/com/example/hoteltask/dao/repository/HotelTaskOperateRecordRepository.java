package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.HotelTaskOperateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelTaskOperateRecordRepository extends JpaRepository<HotelTaskOperateRecord, Long> {
    
    /**
     * Find operation records by task ID
     */
    List<HotelTaskOperateRecord> findByTaskIdOrderByOperateTimeDesc(Long taskId);
    
    /**
     * Find operation records by operator user ID
     */
    List<HotelTaskOperateRecord> findByOperatorUserIdOrderByOperateTimeDesc(Long operatorUserId);
} 
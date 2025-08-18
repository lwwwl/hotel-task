package com.example.hoteltask.dao.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelGuest;

@Repository
public interface HotelGuestRepository extends JpaRepository<HotelGuest, Long> {

    /**
     * 根据id列表查找客人
     */
    List<HotelGuest> findByIdIn(List<Long> ids);
} 
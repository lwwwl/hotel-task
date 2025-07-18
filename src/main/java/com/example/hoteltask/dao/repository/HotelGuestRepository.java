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
     * Find guest by chatwoot ID
     */
    Optional<HotelGuest> findByChatwootId(String chatwootId);
    
    /**
     * Find guests by room number
     */
    List<HotelGuest> findByRoomNumber(String roomNumber);
    
    /**
     * Find guests by verification status
     */
    List<HotelGuest> findByVerifyOrderByCheckInTimeDesc(Short verify);
    
    /**
     * Find guests by check-in time range
     */
    @Query(value = "SELECT g FROM HotelGuest g WHERE g.checkInTime >= :startTime AND g.checkInTime <= :endTime")
    List<HotelGuest> findByCheckInTimeRange(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);
    
    /**
     * Search guests by name
     */
    @Query(value = "SELECT g FROM HotelGuest g WHERE g.guestName LIKE %:keyword%")
    List<HotelGuest> searchByGuestName(@Param("keyword") String keyword);
    
    /**
     * Find guests by phone suffix
     */
    Optional<HotelGuest> findByPhoneSuffix(String phoneSuffix);
} 
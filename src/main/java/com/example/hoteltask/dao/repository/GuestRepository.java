package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    /**
     * Find guest by chatwoot ID
     */
    Optional<Guest> findByChatwootId(String chatwootId);
    
    /**
     * Find guests by room number
     */
    List<Guest> findByRoomNumber(String roomNumber);
    
    /**
     * Find guests by verification status
     */
    List<Guest> findByVerifyOrderByCheckInTimeDesc(Short verify);
    
    /**
     * Find guests by check-in time range
     */
    @Query(value = "SELECT g FROM Guest g WHERE g.checkInTime >= :startTime AND g.checkInTime <= :endTime")
    List<Guest> findByCheckInTimeRange(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);
    
    /**
     * Search guests by name
     */
    @Query(value = "SELECT g FROM Guest g WHERE g.guestName LIKE %:keyword%")
    List<Guest> searchByGuestName(@Param("keyword") String keyword);
    
    /**
     * Find guests by phone suffix
     */
    Optional<Guest> findByPhoneSuffix(String phoneSuffix);
} 
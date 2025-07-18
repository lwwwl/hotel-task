package com.example.hoteltask.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelUser;

@Repository
public interface HotelUserRepository extends JpaRepository<HotelUser, Long> {

    /**
     * Find user by username
     */
    Optional<HotelUser> findByUsername(String username);
    
    /**
     * Find user by email
     */
    Optional<HotelUser> findByEmail(String email);
    
    /**
     * Find user by phone
     */
    Optional<HotelUser> findByPhone(String phone);
    
    /**
     * Find active users
     */
    List<HotelUser> findByActiveOrderByIdDesc(Short active);
    
    /**
     * Find users by employee number
     */
    Optional<HotelUser> findByEmployeeNumber(String employeeNumber);
    
    /**
     * Search users by display name
     */
    @Query(value = "SELECT u FROM HotelUser u WHERE u.displayName LIKE %:keyword% AND u.active = 1")
    List<HotelUser> searchByDisplayName(@Param("keyword") String keyword);
} 
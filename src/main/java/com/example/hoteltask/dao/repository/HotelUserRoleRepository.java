package com.example.hoteltask.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelUserRole;

@Repository
public interface HotelUserRoleRepository extends JpaRepository<HotelUserRole, Long> {

    /**
     * Find user role relations by user ID
     */
    List<HotelUserRole> findByUserId(Long userId);
    
    /**
     * Find user role relations by role ID
     */
    List<HotelUserRole> findByRoleId(Long roleId);
    
    /**
     * Find user IDs by role ID
     */
    @Query(value = "SELECT ur.userId FROM HotelUserRole ur WHERE ur.roleId = :roleId")
    List<Long> findUserIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * Find role IDs by user ID
     */
    @Query(value = "SELECT ur.roleId FROM HotelUserRole ur WHERE ur.userId = :userId")
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
} 
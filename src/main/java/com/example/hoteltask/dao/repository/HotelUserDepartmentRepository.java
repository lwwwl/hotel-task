package com.example.hoteltask.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelUserDepartment;

@Repository
public interface HotelUserDepartmentRepository extends JpaRepository<HotelUserDepartment, Long> {

    /**
     * Find user department relations by user ID
     */
    List<HotelUserDepartment> findByUserId(Long userId);
    
    /**
     * Find user department relations by department ID
     */
    List<HotelUserDepartment> findByDeptId(Long deptId);
    
    /**
     * Find user IDs by department ID
     */
    @Query(value = "SELECT ud.userId FROM HotelUserDepartment ud WHERE ud.deptId = :deptId")
    List<Long> findUserIdsByDeptId(@Param("deptId") Long deptId);
    
    /**
     * Find department IDs by user ID
     */
    @Query(value = "SELECT ud.deptId FROM HotelUserDepartment ud WHERE ud.userId = :userId")
    List<Long> findDeptIdsByUserId(@Param("userId") Long userId);
} 
package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

    /**
     * Find user department relations by user ID
     */
    List<UserDepartment> findByUserId(Long userId);
    
    /**
     * Find user department relations by department ID
     */
    List<UserDepartment> findByDeptId(Long deptId);
    
    /**
     * Find user IDs by department ID
     */
    @Query(value = "SELECT ud.userId FROM UserDepartment ud WHERE ud.deptId = :deptId")
    List<Long> findUserIdsByDeptId(@Param("deptId") Long deptId);
    
    /**
     * Find department IDs by user ID
     */
    @Query(value = "SELECT ud.deptId FROM UserDepartment ud WHERE ud.userId = :userId")
    List<Long> findDeptIdsByUserId(@Param("userId") Long userId);
} 
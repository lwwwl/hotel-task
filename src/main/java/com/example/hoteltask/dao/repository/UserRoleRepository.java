package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Find user role relations by user ID
     */
    List<UserRole> findByUserId(Long userId);
    
    /**
     * Find user role relations by role ID
     */
    List<UserRole> findByRoleId(Long roleId);
    
    /**
     * Find user IDs by role ID
     */
    @Query(value = "SELECT ur.userId FROM UserRole ur WHERE ur.roleId = :roleId")
    List<Long> findUserIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * Find role IDs by user ID
     */
    @Query(value = "SELECT ur.roleId FROM UserRole ur WHERE ur.userId = :userId")
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
} 
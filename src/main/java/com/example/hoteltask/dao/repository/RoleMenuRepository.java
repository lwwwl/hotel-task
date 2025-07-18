package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {

    /**
     * Find role menu relations by role ID
     */
    List<RoleMenu> findByRoleId(Long roleId);
    
    /**
     * Find role menu relations by menu ID
     */
    List<RoleMenu> findByMenuId(Long menuId);
    
    /**
     * Find menu IDs by role ID
     */
    @Query(value = "SELECT rm.menuId FROM RoleMenu rm WHERE rm.roleId = :roleId")
    List<Long> findMenuIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * Find role IDs by menu ID
     */
    @Query(value = "SELECT rm.roleId FROM RoleMenu rm WHERE rm.menuId = :menuId")
    List<Long> findRoleIdsByMenuId(@Param("menuId") Long menuId);
} 
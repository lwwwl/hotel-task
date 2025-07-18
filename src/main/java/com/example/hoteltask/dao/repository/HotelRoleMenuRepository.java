package com.example.hoteltask.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelRoleMenu;

@Repository
public interface HotelRoleMenuRepository extends JpaRepository<HotelRoleMenu, Long> {

    /**
     * Find role menu relations by role ID
     */
    List<HotelRoleMenu> findByRoleId(Long roleId);
    
    /**
     * Find role menu relations by menu ID
     */
    List<HotelRoleMenu> findByMenuId(Long menuId);
    
    /**
     * Find menu IDs by role ID
     */
    @Query(value = "SELECT rm.menuId FROM HotelRoleMenu rm WHERE rm.roleId = :roleId")
    List<Long> findMenuIdsByRoleId(@Param("roleId") Long roleId);
    
    /**
     * Find role IDs by menu ID
     */
    @Query(value = "SELECT rm.roleId FROM HotelRoleMenu rm WHERE rm.menuId = :menuId")
    List<Long> findRoleIdsByMenuId(@Param("menuId") Long menuId);

    /**
     * Find role menu relations by role IDs and menu ID
     */
    @Query(value = "SELECT rm FROM HotelRoleMenu rm WHERE rm.roleId IN :roleIds AND rm.menuId = :menuId")
    List<HotelRoleMenu> findByRoleIdsAndMenuId(@Param("roleIds") List<Long> roleIds, @Param("menuId") Long menuId);
} 
package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * Find menus by parent ID
     */
    List<Menu> findByParentIdOrderBySortOrderAsc(Long parentId);
    
    /**
     * Find menus by type
     */
    List<Menu> findByType(Short type);
    
    /**
     * Find visible menus
     */
    List<Menu> findByVisibleOrderBySortOrderAsc(Boolean visible);
    
    /**
     * Find all top-level menus
     */
    @Query(value = "SELECT m FROM Menu m WHERE m.parentId = 0 AND m.visible = true ORDER BY m.sortOrder ASC")
    List<Menu> findAllVisibleTopLevelMenus();
    
    /**
     * Find menus by path
     */
    List<Menu> findByPathLike(String path);
} 
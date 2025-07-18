package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * Find departments by parent ID
     */
    List<Department> findByParentIdOrderByIdAsc(Long parentId);
    
    /**
     * Find departments by leader user ID
     */
    List<Department> findByLeaderUserId(Long leaderUserId);
    
    /**
     * Find all top-level departments
     */
    @Query(value = "SELECT d FROM Department d WHERE d.parentId = 0 ORDER BY d.id ASC")
    List<Department> findAllTopLevelDepartments();
    
    /**
     * Search departments by name
     */
    @Query(value = "SELECT d FROM Department d WHERE d.name LIKE %:keyword%")
    List<Department> searchByName(@Param("keyword") String keyword);
} 
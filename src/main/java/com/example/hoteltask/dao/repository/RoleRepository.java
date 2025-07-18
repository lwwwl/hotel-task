package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     */
    Optional<Role> findByName(String name);
    
    /**
     * Search roles by name or description
     */
    @Query(value = "SELECT r FROM Role r WHERE r.name LIKE %:keyword% OR r.description LIKE %:keyword%")
    List<Role> searchByNameOrDescription(@Param("keyword") String keyword);
    
    /**
     * Find roles with member count greater than specified value
     */
    List<Role> findByMemberCountGreaterThan(Integer memberCount);
} 
package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by phone
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * Find active users
     */
    List<User> findByActiveOrderByIdDesc(Short active);
    
    /**
     * Find users by employee number
     */
    Optional<User> findByEmployeeNumber(String employeeNumber);
    
    /**
     * Search users by display name
     */
    @Query(value = "SELECT u FROM User u WHERE u.displayName LIKE %:keyword% AND u.active = 1")
    List<User> searchByDisplayName(@Param("keyword") String keyword);
} 
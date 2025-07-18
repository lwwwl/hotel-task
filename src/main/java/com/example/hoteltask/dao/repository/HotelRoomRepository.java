package com.example.hoteltask.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hoteltask.dao.entity.HotelRoom;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {

    /**
     * 根据房间名称查找
     */
    Optional<HotelRoom> findByName(String name);
    
    /**
     * 查找所有有效的房间
     */
    List<HotelRoom> findByActiveOrderByNameAsc(Short active);
    
    /**
     * 根据名称模糊搜索房间
     */
    @Query(value = "SELECT r FROM HotelRoom r WHERE r.name LIKE %:keyword%")
    List<HotelRoom> searchByName(@Param("keyword") String keyword);
    
    /**
     * 查找所有有效房间数量
     */
    long countByActive(Short active);
} 
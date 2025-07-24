package com.example.hoteltask.dao.repository;

import com.example.hoteltask.dao.entity.HotelTaskOperateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelTaskOperateRecordRepository extends JpaRepository<HotelTaskOperateRecord, Long> {

} 
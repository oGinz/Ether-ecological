package com.ether.aether.repository;

import com.ether.aether.model.SensorType;
import com.ether.aether.model.WaterQualityData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface WaterQualityRepository extends JpaRepository<WaterQualityData, Long> {
    
    List<WaterQualityData> findAllByOrderByTimestampDesc();
    Page<WaterQualityData> findAllByOrderByTimestampDesc(Pageable pageable);
    List<WaterQualityData> findBySensorTypeOrderByTimestampDesc(SensorType type);
    WaterQualityData findTopBySensorTypeOrderByTimestampDesc(SensorType type);
    
    @Query("SELECT AVG(w.value) FROM WaterQualityData w WHERE w.sensorType = :type")
    Double avgValueByType(@Param("type") SensorType type);
    
    @Query("SELECT MIN(w.value) FROM WaterQualityData w WHERE w.sensorType = :type")
    Double minValueByType(@Param("type") SensorType type);
    
    @Query("SELECT MAX(w.value) FROM WaterQualityData w WHERE w.sensorType = :type")
    Double maxValueByType(@Param("type") SensorType type);
    
    long countByIsAlertTrueAndTimestampAfter(LocalDateTime timestamp);
}
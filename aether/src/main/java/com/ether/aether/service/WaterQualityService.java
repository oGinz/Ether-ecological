package com.ether.aether.service;

import com.ether.aether.model.SensorType;
import com.ether.aether.model.WaterQualityData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface WaterQualityService {
    
    // Basic CRUD operations
    WaterQualityData saveData(WaterQualityData data);
    
    // Read operations
    List<WaterQualityData> getAllData();
    Page<WaterQualityData> getAllData(Pageable pageable);
    List<WaterQualityData> getReadingsByType(SensorType type);
    WaterQualityData getLatestByType(SensorType type);
    
    // Statistics operations
    Map<String, Object> getQualityStats();
    
    // Utility methods
    boolean isReadingValid(WaterQualityData data);
}
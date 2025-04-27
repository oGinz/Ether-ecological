package com.ether.aether.service.impl;

import com.ether.aether.model.SensorType;
import com.ether.aether.model.WaterQualityData;
import com.ether.aether.repository.WaterQualityRepository;
import com.ether.aether.service.WaterQualityService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class WaterQualityServiceImpl implements WaterQualityService {

    private final WaterQualityRepository repository;

    public WaterQualityServiceImpl(WaterQualityRepository repository) {
        this.repository = repository;
    }

    @Override
    public WaterQualityData saveData(WaterQualityData data) {
        if (!isReadingValid(data)) {
            throw new IllegalArgumentException("Invalid water quality reading");
        }
        data.setTimestamp(LocalDateTime.now());
        return repository.save(data);
    }

    @Override
    public List<WaterQualityData> getAllData() {
        return repository.findAllByOrderByTimestampDesc();
    }

    @Override
    public Page<WaterQualityData> getAllData(Pageable pageable) {
        return repository.findAllByOrderByTimestampDesc(pageable);
    }

    @Override
    public List<WaterQualityData> getReadingsByType(SensorType type) {
        return repository.findBySensorTypeOrderByTimestampDesc(type);
    }

    @Override
    public WaterQualityData getLatestByType(SensorType type) {
        WaterQualityData data = repository.findTopBySensorTypeOrderByTimestampDesc(type);
        if (data == null) {
            throw new NoSuchElementException("No readings found for type: " + type);
        }
        return data;
    }

    @Override
    public Map<String, Object> getQualityStats() {
        return Map.of(
            "totalReadings", repository.count(),
            "temperatureStats", Map.of(
                "average", repository.avgValueByType(SensorType.TEMPERATURE),
                "min", repository.minValueByType(SensorType.TEMPERATURE),
                "max", repository.maxValueByType(SensorType.TEMPERATURE)
            ),
            "phStats", Map.of(
                "average", repository.avgValueByType(SensorType.PH),
                "min", repository.minValueByType(SensorType.PH),
                "max", repository.maxValueByType(SensorType.PH)
            ),
            "alertsLast24Hours", repository.countByIsAlertTrueAndTimestampAfter(
                LocalDateTime.now().minusHours(24))
        );
    }

    @Override
    public boolean isReadingValid(WaterQualityData data) {
        if (data == null || data.getSensorType() == null) {
            return false;
        }
        
        return switch (data.getSensorType()) {
            case TEMPERATURE -> data.getValue() >= -50 && data.getValue() <= 100;
            case PH -> data.getValue() >= 0 && data.getValue() <= 14;
        };
    }
}
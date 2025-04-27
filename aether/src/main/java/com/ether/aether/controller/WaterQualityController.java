package com.ether.aether.controller;

import com.ether.aether.model.SensorType;
import com.ether.aether.model.WaterQualityData;
import com.ether.aether.service.WaterQualityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/water-quality")
public class WaterQualityController {

    private final WaterQualityService service;

    // Single constructor with @Autowired (constructor injection)
    public WaterQualityController(WaterQualityService service) {
        this.service = service;
    }

    // Get all readings
    @GetMapping
    public List<WaterQualityData> getAllData() {
        return service.getAllData();
    }

    // Get readings by type
    @GetMapping("/{type}")
    public List<WaterQualityData> getReadingsByType(@PathVariable String type) {
        return service.getReadingsByType(SensorType.valueOf(type.toUpperCase()));
    }

    // Get latest readings of both types
    @GetMapping("/latest")
    public Map<String, Object> getLatestReadings() {
        return Map.of(
            "temperature", service.getLatestByType(SensorType.TEMPERATURE),
            "ph", service.getLatestByType(SensorType.PH)
        );
    }

    // Get statistics
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return service.getQualityStats();
    }

    // Add new reading
    @PostMapping
    public WaterQualityData addReading(@RequestBody WaterQualityData data) {
        return service.saveData(data);
    }
}
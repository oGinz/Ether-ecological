package com.ether.aether.scheduler;

import com.ether.aether.model.WaterQualityData;
import com.ether.aether.service.WaterQualityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ether.aether.model.SensorType;

import java.util.Random;

@Component
public class SensorSimulatorScheduler {
    private final WaterQualityService service;
    private final Random random = new Random();

    public SensorSimulatorScheduler(WaterQualityService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 5000)
    public void simulateTemperatureSensor() {
        double temp = 10 + random.nextDouble() * 30; // 10°C - 40°C
        service.saveData(new WaterQualityData(SensorType.TEMPERATURE, temp));
    }
    
    @Scheduled(fixedRate = 5000)
    public void simulatePHSensor() {
        double ph = 4 + random.nextDouble() * 10; // 4 - 14 pH
        service.saveData(new WaterQualityData(SensorType.PH, ph));
    }



}


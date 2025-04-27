package com.ether.aether.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "water_quality")
public class WaterQualityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('TEMPERATURE', 'PH')")
    private SensorType sensorType;   

    private double value;
    private boolean isAlert;
    private LocalDateTime timestamp = LocalDateTime.now();

    @PrePersist
    public void checkAlert() {
        this.isAlert = switch (this.sensorType) {
            case TEMPERATURE -> (this.value < 0 || this.value > 30);
            case PH -> (this.value < 6.5 || this.value > 8.5);
        };
    }

     public WaterQualityData() {}

     public WaterQualityData(SensorType sensorType, double value) {
         this.sensorType = sensorType;
         this.value = value;

     }
 
     public Long getId() { return id; }
     public void setId(Long id) { this.id = id; }
     public SensorType getSensorType() { return sensorType; }
     public void setSensorType(SensorType sensorType) { this.sensorType = sensorType; }
     public double getValue() { return value; }
     public void setValue(double value) { this.value = value; }
     public boolean isAlert() { return isAlert; }
     public void setAlert(boolean alert) { isAlert = alert; }
     public LocalDateTime getTimestamp() { return timestamp; }
     public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

     public WaterQualityData orElseThrow(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
     }
 }
  



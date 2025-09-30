package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private int maxCar;
    private List<String> enabledSorts;

    public int getMaxCar() { return maxCar; }
    public void setMaxCar(int maxCar) { this.maxCar = maxCar; }

    public List<String> getEnabledSorts() { return enabledSorts; }
    public void setEnabledSorts(List<String> enabledSorts) { this.enabledSorts = enabledSorts; }
}


package com.example.controller;

import com.example.model.Car;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarApiController {
    private final CarService carService;

    @Autowired
    public CarApiController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCarsByUser(@RequestParam(required = false) Long userId) {
        return carService.getCarsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
}

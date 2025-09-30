package com.example.service;

import com.example.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getCars(Long count, String sortBy);
}

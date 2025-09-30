package com.example.controller;

import com.example.model.Car;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getCars(@RequestParam(name = "count", required = false) Long count,
                          @RequestParam(name = "sortBy", required = false) String sortBy,
                          Model model) {
        List<Car> cars = carService.getCars(count, sortBy);
        model.addAttribute("cars", cars);
        model.addAttribute("count", count);
        model.addAttribute("sortBy", sortBy);
        return "cars";
    }

}

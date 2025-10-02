package com.example.service;

import com.example.config.AppConfig;
import com.example.model.Car;
import com.example.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final AppConfig config;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, AppConfig config) {
        this.carRepository = carRepository;
        this.config = config;
    }

    public List<Car> getCars(Long count, String sortBy) {

        initializeTestCars();

        if (sortBy != null && !sortBy.isEmpty()) {
            validateSortField(sortBy);
        }

        long carCount = (count == null || count >= config.getMaxCar()) ? config.getMaxCar() : count;

        List<Car> cars = carRepository.findAll();

        if (sortBy != null && !sortBy.isEmpty()) {
            cars = sortCars(cars, sortBy);
        }

        return cars.stream().limit(carCount).toList();
    }

    public List<Car> getCarsByUserId(Long userId) {
        if (userId != null) {
            return carRepository.findCarByUserId(userId);
        }
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    private List<Car> sortCars(List<Car> cars, String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "id" -> cars.stream()
                    .sorted(Comparator.comparing(Car::getId))
                    .toList();
            case "model" -> cars.stream()
                    .sorted((c1, c2) -> c1.getModel().compareToIgnoreCase(c2.getModel()))
                    .toList();
            case "price" -> cars.stream()
                    .sorted(Comparator.comparing(Car::getPrice))
                    .toList();
            case "year" -> cars.stream()
                    .sorted(Comparator.comparing(Car::getYear))
                    .toList();
            default -> cars;
        };
    }

    private void validateSortField(String sortBy) {
        if (!config.getEnabledSorts().contains(sortBy.toLowerCase())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Сортировка по полю '" + sortBy + "' отключена"
            );
        }
    }

    public void initializeTestCars() {
        if (carRepository.count() == 0) {
            carRepository.save(new Car("Toyota Camry", 2022, 2385000.0, 1L));
            carRepository.save(new Car("Honda Civic", 2023, 2565000.0, 2L));
            carRepository.save(new Car("BMW X5", 2021, 6165000.0, 3L));
            carRepository.save(new Car("Mercedes C-Class", 2022, 4401000.0, 4L));
            carRepository.save(new Car("Audi A4", 2023, 3861000.0, 5L));
            carRepository.save(new Car("Ford Mustang", 2022, 3501000.0, 6L));
            carRepository.save(new Car("Tesla Model 3", 2023, 3869100.0, 7L));
            carRepository.save(new Car("Volkswagen Golf", 2021, 2249550.0, 8L));
            carRepository.save(new Car("Hyundai Tucson", 2022, 2965500.0, 9L));
            carRepository.save(new Car("Kia Sportage", 2023, 2511000.0, 10L));
        }
    }
}

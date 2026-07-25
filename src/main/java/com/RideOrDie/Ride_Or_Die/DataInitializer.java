package com.RideOrDie.Ride_Or_Die;

import com.RideOrDie.Ride_Or_Die.model.Category;
import com.RideOrDie.Ride_Or_Die.model.Product;
import com.RideOrDie.Ride_Or_Die.repository.CategoryRepository;
import com.RideOrDie.Ride_Or_Die.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            Category kabatok = new Category("Kabátok", "Prémium bőr és textil motoros kabátok", "kabatok.jpg", true);
            Category kesztyuk = new Category("Kesztyűk", "Nyári, téli és verseny kesztyűk", "kesztyuk.jpg", true);

            categoryRepository.save(kabatok);
            categoryRepository.save(kesztyuk);

            Product kabat1 = new Product("Alpinestars GP Plus R V3", "Prémium marhabőr dzseki", 185000.0, "kabat1.jpg", 10, kabatok);
            Product kesztyu1 = new Product("GP Pro R4 Kesztyű", "Bivalyerős pályás védelem", 68500.0, "kesztyuhosszu.jpg", 15, kesztyuk);

            productRepository.save(kabat1);
            productRepository.save(kesztyu1);
        }
    }
}

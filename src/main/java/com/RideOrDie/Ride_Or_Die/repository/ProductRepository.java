package com.RideOrDie.Ride_Or_Die.repository;

import com.RideOrDie.Ride_Or_Die.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Egyedi lekérdezés: Termékek keresése kategória ID alapján
    List<Product> findByCategoryId(Long categoryId);
}
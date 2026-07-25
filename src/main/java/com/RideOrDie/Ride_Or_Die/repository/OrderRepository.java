package com.RideOrDie.Ride_Or_Die.repository;

import com.RideOrDie.Ride_Or_Die.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
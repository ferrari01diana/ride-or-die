package com.RideOrDie.Ride_Or_Die.service;

import com.RideOrDie.Ride_Or_Die.dto.OrderDTO;
import com.RideOrDie.Ride_Or_Die.exception.ResourceNotFoundException;
import com.RideOrDie.Ride_Or_Die.model.Order;
import com.RideOrDie.Ride_Or_Die.model.Product;
import com.RideOrDie.Ride_Or_Die.repository.OrderRepository;
import com.RideOrDie.Ride_Or_Die.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // Rendelés leadása
    public OrderDTO createOrder(OrderDTO dto) {
        log.info("Új rendelés feldolgozása a következő vásárlónak: {}", dto.getCustomerName());

        // Termékek megkeresése az ID-k alapján Stream segítségével
        List<Product> products = dto.getProductIds().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("A rendelésben szereplő termék nem található: " + id)))
                .toList();

        // Végösszeg kiszámítása Stream-mel
        Double totalPrice = products.stream()
                .mapToDouble(Product::getPrice) // Method Reference
                .sum();

        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setShippingAddress(dto.getShippingAddress());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);
        log.info("Rendelés sikeresen elmentve, ID: {}", savedOrder.getId());

        return convertToDTO(savedOrder);
    }

    // Összes rendelés lekérése
    public List<OrderDTO> getAllOrders() {
        log.info("Összes rendelés lekérése...");
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private OrderDTO convertToDTO(Order order) {
        List<Long> productIds = order.getProducts().stream()
                .map(Product::getId)
                .toList();

        return new OrderDTO(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                order.getOrderDate(),
                productIds
        );
    }
}
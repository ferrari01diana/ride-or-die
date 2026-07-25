package com.RideOrDie.Ride_Or_Die;

import com.RideOrDie.Ride_Or_Die.dto.ProductDTO;
import com.RideOrDie.Ride_Or_Die.exception.ResourceNotFoundException;
import com.RideOrDie.Ride_Or_Die.model.Category;
import com.RideOrDie.Ride_Or_Die.model.Product;
import com.RideOrDie.Ride_Or_Die.repository.CategoryRepository;
import com.RideOrDie.Ride_Or_Die.repository.ProductRepository;
import com.RideOrDie.Ride_Or_Die.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetProductById_Success() {
        // ARRANGE (Előkészítés)
        Category category = new Category("Kabátok", "Leírás", "kabat.jpg", true);
        Product product = new Product("Bőrdzseki", "Szuper dzseki", 120000.0, "dzseki.jpg", 5, category);
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // ACT (Cselekvés)
        ProductDTO result = productService.getProductById(1L);

        // ASSERT (Ellenőrzés)
        assertNotNull(result);
        assertEquals("Bőrdzseki", result.getName());
        assertEquals(120000.0, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductById_NotFound_ThrowsException() {
        // ARRANGE (Előkészítés)
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT (Cselekvés és Ellenőrzés egyben az Exceptionre)
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(99L);
        });

        verify(productRepository, times(1)).findById(99L);
    }
}
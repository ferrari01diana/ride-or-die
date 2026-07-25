package com.RideOrDie.Ride_Or_Die.service;

import com.RideOrDie.Ride_Or_Die.dto.ProductDTO;
import com.RideOrDie.Ride_Or_Die.exception.ResourceNotFoundException;
import com.RideOrDie.Ride_Or_Die.model.Category;
import com.RideOrDie.Ride_Or_Die.model.Product;
import com.RideOrDie.Ride_Or_Die.repository.CategoryRepository;
import com.RideOrDie.Ride_Or_Die.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Összes termék lekérése DTO-ként (Stream + Lambda + Method Reference használatával!)
    public List<ProductDTO> getAllProducts() {
        log.info("Összes termék lekérése az adatbázisból...");
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO) // Method Reference!
                .toList();
    }

    // Egy termék lekérése ID alapján
    public ProductDTO getProductById(Long id) {
        log.info("Termék keresése ID alapján: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nem található termék ezzel az ID-val: " + id));
        return convertToDTO(product);
    }

    // Termékek lekérése kategória alapján
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        log.info("Termékek lekérése kategória ID alapján: {}", categoryId);
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Új termék mentése
    public ProductDTO createProduct(ProductDTO dto) {
        log.info("Új termék mentése: {}", dto.getName());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategória nem található: " + dto.getCategoryId()));

        Product product = new Product(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getImageUrl(),
                dto.getStock(),
                category
        );

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Termék törlése (CRUD - Delete)
    public void deleteProduct(Long id) {
        log.warn("Termék törlése, ID: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Törlés sikertelen, termék nem létezik: " + id);
        }
        productRepository.deleteById(id);
    }

    // Segédmetódus: Entity -> DTO konvertálás
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
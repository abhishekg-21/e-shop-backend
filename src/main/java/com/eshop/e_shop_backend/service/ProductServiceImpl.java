// src/main/java/com/eshop/e_shop_backend/service/ProductServiceImpl.java
package com.eshop.e_shop_backend.service;

import com.eshop.e_shop_backend.dto.ProductDTO;
import com.eshop.e_shop_backend.model.Category;
import com.eshop.e_shop_backend.model.Product;
import com.eshop.e_shop_backend.repository.CategoryRepository;
import com.eshop.e_shop_backend.repository.ProductRepository;
import org.springframework.data.domain.Page; // Import Page
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ProductService for managing product data.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all products from the database with pagination.
     * Converts Product entities to ProductDTOs before returning.
     *
     * @param pageable Pagination information (page number, size, sort).
     * @return A Page of ProductDTOs.
     */
    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) { // MODIFIED: Accepts Pageable
        logger.info("Fetching products from the database with pagination: {}", pageable); // DEBUG
        Page<Product> productPage = productRepository.findAll(pageable); // Use findAll(Pageable)
        logger.info("Found {} products on page {} of {}.",
                productPage.getNumberOfElements(), productPage.getNumber() + 1, productPage.getTotalPages()); // DEBUG

        return productPage.map(product -> { // Use map to convert Page<Product> to Page<ProductDTO>
            ProductDTO dto = new ProductDTO(product);
            logger.debug("Converted Product (ID: {}) to DTO: {}", product.getId(), dto);
            return dto;
        });
    }

    @Override
    public Optional<ProductDTO> getProductById(String id) {
        logger.info("Attempting to retrieve product by ID: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductDTO dto = new ProductDTO(productOptional.get());
            logger.info("Found product with ID {} and converted to DTO: {}", id, dto);
            return Optional.of(dto);
        } else {
            logger.info("Product with ID {} not found.", id);
            return Optional.empty();
        }
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Creating new product with name: {}", productDTO.getName());
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setStockQuantity(productDTO.getStockQuantity());
        newProduct.setImageUrl(productDTO.getImageUrl());

        Category category = categoryRepository.findByName(productDTO.getCategory())
                .orElseThrow(() -> {
                    logger.error("Category not found: {}", productDTO.getCategory());
                    return new RuntimeException("Category not found: " + productDTO.getCategory());
                });
        newProduct.setCategory(category);

        Product savedProduct = productRepository.save(newProduct);
        logger.info("Product created with ID: {}", savedProduct.getId());
        return new ProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", id);
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            logger.warn("Product with ID {} not found for update.", id);
            return null;
        }
        Product existingProduct = existingProductOpt.get();

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setImageUrl(productDTO.getImageUrl());

        if (productDTO.getCategory() != null &&
                (existingProduct.getCategory() == null
                        || !existingProduct.getCategory().getName().equals(productDTO.getCategory()))) {
            Category category = categoryRepository.findByName(productDTO.getCategory())
                    .orElseThrow(() -> {
                        logger.error("Category not found for update: {}", productDTO.getCategory());
                        return new RuntimeException("Category not found: " + productDTO.getCategory());
                    });
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product with ID {} updated successfully.", updatedProduct.getId());
        return new ProductDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.info("Product with ID {} deleted.", id);
    }
}
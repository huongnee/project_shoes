package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.dto.ProductImagesDTO;
import com.example.project_shoes.dto.ProductConfigDTO;
import com.example.project_shoes.entity.Product;
import com.example.project_shoes.entity.ProductImages;
import com.example.project_shoes.entity.ProductConfig;
import com.example.project_shoes.mapper.ProductMapper;
import com.example.project_shoes.repository.ProductRepository;
import com.example.project_shoes.repository.ProductImagesRepository;
import com.example.project_shoes.repository.ProductConfigRepository;
import com.example.project_shoes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductImagesRepository productImagesRepository;
    
    @Autowired
    private ProductConfigRepository productConfigRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAllActive() {
        try {
            // Phương pháp 0: Sử dụng native query
            List<Product> nativeProducts = productRepository.findAllNotDeleted();
            if (!nativeProducts.isEmpty()) {
                System.out.println("Native query tìm thấy: " + nativeProducts.size() + " sản phẩm");
                return productMapper.toDTOList(nativeProducts);
            }
            
            // Phương pháp 0.5: Sử dụng findAllProducts
            List<Product> allDbProducts = productRepository.findAllProducts();
            if (!allDbProducts.isEmpty()) {
                System.out.println("findAllProducts tìm thấy: " + allDbProducts.size() + " sản phẩm");
                return productMapper.toDTOList(allDbProducts);
            }
            
            // Phương pháp 1: Sử dụng findAll
            List<Product> allProducts = productRepository.findAll();
            if (!allProducts.isEmpty()) {
                System.out.println("findAll tìm thấy: " + allProducts.size() + " sản phẩm");
                return productMapper.toDTOList(allProducts);
            }
            
            // Phương pháp 2: Sử dụng findByIsDeleteFalse
            List<Product> products = productRepository.findByIsDeleteFalse();
            if (!products.isEmpty()) {
                System.out.println("findByIsDeleteFalse tìm thấy: " + products.size() + " sản phẩm");
                return productMapper.toDTOList(products);
            }
            
            // Phương pháp 3: Sử dụng findByIsActiveTrueAndIsDeleteFalse
            List<Product> activeProducts = productRepository.findByIsActiveTrueAndIsDeleteFalse();
            if (!activeProducts.isEmpty()) {
                System.out.println("findByIsActiveTrueAndIsDeleteFalse tìm thấy: " + activeProducts.size() + " sản phẩm");
                return productMapper.toDTOList(activeProducts);
            }
            
            System.out.println("Không tìm thấy sản phẩm nào với tất cả các phương pháp");
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ProductDTO> searchByName(String name) {
        List<Product> products = productRepository.findByNameContainingAndIsDeleteFalse(name);
        return productMapper.toDTOList(products);
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findByIdAndIsDeleteFalse(id);
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByIdCategoryAndIsDeleteFalse(categoryId);
        return productMapper.toDTOList(products);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setIsDelete(true);
            productRepository.save(product);
        }
    }

    @Override
    public void updateMainImage(Long productId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        product.setImage(imageUrl);
        productRepository.save(product);
    }

    @Override
    public void saveProductImages(List<ProductImagesDTO> images) {
        // Implementation needed
    }

    @Override
    @Transactional
    public void saveProductConfigs(List<ProductConfigDTO> configs) {
        if (configs == null || configs.isEmpty()) {
            System.out.println("Không có configs để lưu");
            return;
        }

        try {
            System.out.println("Bắt đầu chuyển đổi " + configs.size() + " configs sang entities");
            List<ProductConfig> productConfigs = configs.stream()
                .map(dto -> {
                    ProductConfig config = new ProductConfig();
                    config.setIdProduct(dto.getIdProduct());
                    config.setIdConfig(dto.getIdConfig());
                    config.setValue(dto.getValue());
                    System.out.println("Đã chuyển đổi config: " + config);
                    return config;
                })
                .collect(Collectors.toList());

            System.out.println("Bắt đầu lưu " + productConfigs.size() + " configs vào database");
            List<ProductConfig> savedConfigs = productConfigRepository.saveAll(productConfigs);
            System.out.println("Đã lưu thành công " + savedConfigs.size() + " configs");
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu product configs: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
} 
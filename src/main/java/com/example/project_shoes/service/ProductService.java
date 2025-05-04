package com.example.project_shoes.service;

import com.example.project_shoes.dto.ProductDTO;
import com.example.project_shoes.entity.Product;
import com.example.project_shoes.mapper.ProductMapper;
import com.example.project_shoes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

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

    public List<ProductDTO> searchByName(String name) {
        List<Product> products = productRepository.findByNameContainingAndIsDeleteFalse(name);
        return productMapper.toDTOList(products);
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findByIdAndIsDeleteFalse(id);
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByIdCategoryAndIsDeleteFalse(categoryId);
        return productMapper.toDTOList(products);
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setIsDelete(true);
            productRepository.save(product);
        }
    }
} 
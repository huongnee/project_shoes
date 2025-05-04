package com.example.project_shoes.service.impl;

import com.example.project_shoes.dto.CategoryDTO;
import com.example.project_shoes.entity.Category;
import com.example.project_shoes.mapper.CategoryMapper;
import com.example.project_shoes.repository.CategoryRepository;
import com.example.project_shoes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findByIsDeleteFalse();
        return categoryMapper.toDTOList(categories);
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findByIdAndIsDeleteFalse(id);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        // Đảm bảo các giá trị boolean không bị null
        if (category.getIsDelete() == null) {
            category.setIsDelete(false);
        }
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByIdAndIsDeleteFalse(categoryDTO.getId());
        if (existingCategory != null) {
            Category category = categoryMapper.toEntity(categoryDTO);
            // Đảm bảo các giá trị boolean không bị null
            if (category.getIsDelete() == null) {
                category.setIsDelete(false);
            }
            if (category.getIsActive() == null) {
                category.setIsActive(true);
            }
            Category updatedCategory = categoryRepository.save(category);
            return categoryMapper.toDTO(updatedCategory);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Category existingCategory = categoryRepository.findByIdAndIsDeleteFalse(id);
        if (existingCategory != null) {
            existingCategory.setIsDelete(true);
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingAndIsDeleteFalse(name);
        return categoryMapper.toDTOList(categories);
    }
    
    @Override
    public List<CategoryDTO> findAllActive() {
        try {
            // Sử dụng native query mới để lấy tất cả danh mục có isActive=1 và isDelete=0
            List<Category> categories = categoryRepository.findActiveNotDeletedNative();
            if (!categories.isEmpty()) {
                System.out.println("Native query (active=1, delete=0) tìm thấy: " + categories.size() + " danh mục");
                return mapToCategoryDTOList(categories);
            }
            
            // Thử lấy tất cả danh mục không quan tâm đến isActive và isDelete
            List<Category> allCategories = categoryRepository.findAllByNativeQuery();
            if (!allCategories.isEmpty()) {
                System.out.println("Native query (tất cả) tìm thấy: " + allCategories.size() + " danh mục");
                return mapToCategoryDTOList(allCategories);
            }
            
            // Các phương thức truy vấn cũ làm dự phòng
            categories = categoryRepository.findAllNotDeleted();
            if (!categories.isEmpty()) {
                System.out.println("Native query tìm thấy: " + categories.size() + " danh mục");
                return mapToCategoryDTOList(categories);
            }
            
            List<Category> activeCategories = categoryRepository.findByIsActiveTrueAndIsDeleteFalse();
            if (!activeCategories.isEmpty()) {
                System.out.println("JPQL tìm thấy: " + activeCategories.size() + " danh mục");
                return mapToCategoryDTOList(activeCategories);
            }
            
            allCategories = categoryRepository.findAll();
            if (!allCategories.isEmpty()) {
                System.out.println("findAll tìm thấy: " + allCategories.size() + " danh mục");
                // Lọc thủ công để đảm bảo chỉ lấy các danh mục active và không bị xóa
                List<Category> filteredCategories = allCategories.stream()
                    .filter(c -> (c.getIsActive() == null || c.getIsActive()) && 
                                (c.getIsDelete() == null || !c.getIsDelete()))
                    .collect(Collectors.toList());
                System.out.println("Sau khi lọc: " + filteredCategories.size() + " danh mục");
                return mapToCategoryDTOList(filteredCategories);
            }
            
            System.out.println("Không tìm thấy danh mục nào");
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm danh mục: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<CategoryDTO> findParentCategories() {
        try {
            List<Category> parentCategories = categoryRepository.findByIdParentIsNull();
            if (!parentCategories.isEmpty()) {
                // Lọc thêm những danh mục không bị xóa
                List<Category> activeParentCategories = parentCategories.stream()
                    .filter(c -> c.getIsDelete() == null || !c.getIsDelete())
                    .collect(Collectors.toList());
                System.out.println("Tìm thấy: " + activeParentCategories.size() + " danh mục gốc");
                return mapToCategoryDTOList(activeParentCategories);
            }
            
            System.out.println("Không tìm thấy danh mục gốc nào");
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm danh mục gốc: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<CategoryDTO> findSubCategories(Long parentId) {
        try {
            List<Category> subCategories = categoryRepository.findByIdParent(parentId);
            if (!subCategories.isEmpty()) {
                // Lọc thêm những danh mục không bị xóa
                List<Category> activeSubCategories = subCategories.stream()
                    .filter(c -> c.getIsDelete() == null || !c.getIsDelete())
                    .collect(Collectors.toList());
                System.out.println("Tìm thấy: " + activeSubCategories.size() + " danh mục con của danh mục " + parentId);
                return mapToCategoryDTOList(activeSubCategories);
            }
            
            System.out.println("Không tìm thấy danh mục con nào của danh mục " + parentId);
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm danh mục con: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    private CategoryDTO mapToCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIcon(category.getIcon());
        dto.setIdParent(category.getIdParent());
        dto.setSlug(category.getSlug());
        dto.setNotes(category.getNotes());
        // Đảm bảo các giá trị boolean không bị null
        dto.setIsActive(category.getIsActive() == null ? true : category.getIsActive());
        dto.setIsDelete(category.getIsDelete() == null ? false : category.getIsDelete());
        dto.setMetaTitle(category.getMetaTitle());
        dto.setMetaKeyword(category.getMetaKeyword());
        dto.setMetaDescription(category.getMetaDescription());
        dto.setCreatedBy(category.getCreatedBy());
        dto.setCreatedDate(category.getCreatedDate());
        dto.setUpdatedBy(category.getUpdatedBy());
        dto.setUpdatedDate(category.getUpdatedDate());
        return dto;
    }
    
    private List<CategoryDTO> mapToCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryDTO)
                .collect(Collectors.toList());
    }
} 
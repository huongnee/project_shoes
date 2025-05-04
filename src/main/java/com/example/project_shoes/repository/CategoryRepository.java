package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.isDelete = false OR c.isDelete IS NULL")
    List<Category> findByIsDeleteFalse();
    
    @Query("SELECT c FROM Category c WHERE (c.isActive = true OR c.isActive IS NULL) AND (c.isDelete = false OR c.isDelete IS NULL)")
    List<Category> findByIsActiveTrueAndIsDeleteFalse();
    
    @Query(value = "SELECT * FROM category WHERE ISDELETE = 0 OR ISDELETE IS NULL", nativeQuery = true)
    List<Category> findAllNotDeleted();
    
    @Query(value = "SELECT * FROM category", nativeQuery = true)
    List<Category> findAllByNativeQuery();
    
    @Query(value = "SELECT * FROM category WHERE (ISACTIVE = 1 OR ISACTIVE IS NULL) AND (ISDELETE = 0 OR ISDELETE IS NULL)", nativeQuery = true)
    List<Category> findActiveNotDeletedNative();
    
    List<Category> findByIdParent(Long idParent);
    
    List<Category> findByIdParentIsNull();
    
    @Query("SELECT c FROM Category c WHERE c.id = :id AND (c.isDelete = false OR c.isDelete IS NULL)")
    Category findByIdAndIsDeleteFalse(@Param("id") Long id);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name% AND (c.isDelete = false OR c.isDelete IS NULL)")
    List<Category> findByNameContainingAndIsDeleteFalse(@Param("name") String name);
}
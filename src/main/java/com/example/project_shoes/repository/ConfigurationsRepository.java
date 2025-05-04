package com.example.project_shoes.repository;

import com.example.project_shoes.entity.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationsRepository extends JpaRepository<Configurations, Long> {
    @Query("SELECT c FROM Configurations c WHERE c.isDelete = false OR c.isDelete IS NULL")
    List<Configurations> findByIsDeleteFalse();
    
    @Query("SELECT c FROM Configurations c WHERE (c.isActive = true OR c.isActive IS NULL) AND (c.isDelete = false OR c.isDelete IS NULL)")
    List<Configurations> findByIsActiveTrueAndIsDeleteFalse();
    
    @Query(value = "SELECT * FROM configurations WHERE ISDELETE = 0 OR ISDELETE IS NULL", nativeQuery = true)
    List<Configurations> findAllNotDeleted();
    
    @Query("SELECT c FROM Configurations c WHERE c.id = :id AND (c.isDelete = false OR c.isDelete IS NULL)")
    Configurations findByIdAndIsDeleteFalse(@Param("id") Long id);
    
    @Query("SELECT c FROM Configurations c WHERE c.name LIKE %:name% AND (c.isDelete = false OR c.isDelete IS NULL)")
    List<Configurations> findByNameContainingAndIsDeleteFalse(@Param("name") String name);
} 
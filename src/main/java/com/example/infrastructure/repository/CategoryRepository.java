package com.example.infrastructure.repository;

import com.example.domain.entity.Category;
import com.example.domain.entity.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Category findByTitle(String categoryTitle);
    
    @Transactional
    @Modifying
    void deleteById(Long id);
    
    @Transactional
    @Modifying
    @Query("update Category c set c.title = :title")
    int updateTitleBy(@Param("title") String title);
}

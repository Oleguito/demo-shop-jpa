package com.example.infrastructure.repository;

import com.example.domain.entity.Category;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Category findByTitle(String categoryTitle);
}

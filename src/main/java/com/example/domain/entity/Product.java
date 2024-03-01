package com.example.domain.entity;

import com.example.settings.Settings;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.example.settings.Settings.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = PRODUCTS_TABLE_NAME)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @ManyToOne
    private Category category;
    
    public String getCategoryTitle() {
        return getCategory().getTitle();
    }
}

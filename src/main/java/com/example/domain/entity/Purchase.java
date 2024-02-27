package com.example.domain.entity;

import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Purchase extends BaseEntity {
    
    @ManyToOne
    User user;
}

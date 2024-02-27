package com.example.domain.entity;

import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    
    String login;
    
    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;

}

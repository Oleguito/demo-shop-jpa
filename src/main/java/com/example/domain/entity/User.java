package com.example.domain.entity;

import com.example.domain.valueobject.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String login;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JoinColumn(table = "purchase", name = "user_id")
    private List<Purchase> purchases;

}

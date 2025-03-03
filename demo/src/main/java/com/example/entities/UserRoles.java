package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users_roles")
public class UserRoles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="users_roles_id")
    private Integer id;

    @Column(name ="user_id")
    private Integer user_id;

    @Column(name ="role_id")
    private Integer role_id;
}
package com.mzcteam01.mzcproject01be.domains.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Column(name = "address_code", length = 5)
    private String addressCode;

    @Lob
    @Column(name = "address_detail")
    private String addressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "type")
    private Integer type;

    @Column(name = "registered_at")
    private LocalDateTime registerdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;


}
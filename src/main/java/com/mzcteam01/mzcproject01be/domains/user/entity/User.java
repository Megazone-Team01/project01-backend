package com.mzcteam01.mzcproject01be.domains.user.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Column(name = "address", columnDefinition = "Text")
    private String address;

    @Column(name = "address_detail", columnDefinition = "Text")
    private String addressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole role;

    @Column(name = "type")
    private Integer type;

    @CreatedDate
    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column( name = "updated_at" )
    private LocalDateTime updatedAt;

    @Column( name = "deleted_at" )
    private LocalDateTime deletedAt;

    @Column( name = "profile_img", nullable = true )
    private String profileImg;
    // 리프레시 토큰을 위한 리프레시 토큰과 리프레시 토큰 만료일 컬럼
    private String refreshToken;

    private LocalDateTime refreshTokenExpireAt;

    public void update( String phone, String addressCode, String addressDetail, Integer type ){
        if( phone != null ) this.phone = phone;
        if( address != null ) this.address = address;
        if( addressDetail != null ) this.addressDetail = addressDetail;
        if( type != null ) this.type = type;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(){ this.deletedAt = LocalDateTime.now(); }

    // 리프레시 토큰을 위한 업데이트
    public void updateRefreshToken(String token, LocalDateTime expireAt) {
        this.refreshToken = token;
        this.refreshTokenExpireAt = expireAt;
    }
}
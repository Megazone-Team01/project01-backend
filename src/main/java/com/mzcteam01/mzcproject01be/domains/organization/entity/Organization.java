package com.mzcteam01.mzcproject01be.domains.organization.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "organization")
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "address_code", nullable = false, length = 5)
    private String addressCode;

    @Lob
    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Column(name = "tel", nullable = false, length = 11)
    private String tel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "description")
    private String description;

    @Column(name = "is_online")
    private boolean isOnline;

    @Column(name = "webpage")
    private String webpage;

    @Column(name = "lead_image")
    private String leadImage;
}

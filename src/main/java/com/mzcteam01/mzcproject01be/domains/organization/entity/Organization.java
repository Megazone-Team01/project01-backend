package com.mzcteam01.mzcproject01be.domains.organization.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "organization")
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

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

    @Column(name = "is_online") // 0: 모두 1: 온라인 2: 오프라인
    private int isOnline;

    @Column(name = "webpage")
    private String webpage;

    @Column(name = "lead_image")
    private String leadImage;

    public void update( String address, String addressDetail, String tel, String homepage,
                        Integer type, String description ){
        if( address != null ) this.address = address;
        if( addressDetail != null ) this.addressDetail = addressDetail;
        if( tel != null ) this.tel = tel;
        if( homepage != null ) this.webpage = homepage;
        if( type != null ) this.isOnline = type;
        if( description != null ) this.description = description;
    }

    public void updateStatus( boolean isApprove ){
        if( isApprove ) this.status = 1;
        else this.status = -1;
    }
}

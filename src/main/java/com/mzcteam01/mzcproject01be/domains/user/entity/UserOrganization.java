package com.mzcteam01.mzcproject01be.domains.user.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_organization")
public class UserOrganization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    // 조직 가입 요청 상태 : -1(거절), 0(대기), 1(승인)
    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;


    // 업데이트 메소드
    public void update(int status) {
        if( status != 0 ) this.status = status;
    }
}

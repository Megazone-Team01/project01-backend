package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@MappedSuperclass
public abstract class Meeting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    // 승인여부
    // -1 (반려), 0 (대기), 1(승인)
    @Column(name = "status", nullable = false)
    protected int status;

    @Column(name = "reject_reason", nullable = false)
    private String rejectReason;

    public void update(String name, LocalDateTime startAt, LocalDateTime endAt) {
        if( name != null ) this.name = name;
        if( startAt != null ) this.startAt = startAt;
        if( endAt != null ) this.endAt = endAt;
    }

    public void approve() {
        this.status = 1;
    }

    public void reject(String reason) {
        this.status = -1;
        this.rejectReason = reason;
    }

    public void setCreatedBy(int userId) {
        this.createdBy = userId;
    }

}

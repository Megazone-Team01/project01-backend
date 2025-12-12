package com.mzcteam01.mzcproject01be.domains.lecture.entity;

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
public abstract class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(length = 50, nullable = false, name = "category")
    private String category;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}

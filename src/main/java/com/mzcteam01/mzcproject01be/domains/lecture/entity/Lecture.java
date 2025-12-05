package com.mzcteam01.mzcproject01be.domains.lecture.entity;

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
public abstract class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, name = "name")
    private String name;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "organization_id")
//    private Organization organization_id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id")
//    private User teacher;

    @Column(length = 50, nullable = false, name = "category")
    private String category;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Lob
    @Column(name = "description")
    private String description;

}

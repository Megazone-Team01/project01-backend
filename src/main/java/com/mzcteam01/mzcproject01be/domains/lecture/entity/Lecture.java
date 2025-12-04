package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "price")
    private int price;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "description")
    private String description;

    protected Lecture() {}
}

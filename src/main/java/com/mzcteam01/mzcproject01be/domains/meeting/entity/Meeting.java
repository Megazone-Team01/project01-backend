package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 50)
    private String name;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "organization_id")
//    private Organization organization_id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id")
//    private User teacher;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id")
//    private User student;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

}

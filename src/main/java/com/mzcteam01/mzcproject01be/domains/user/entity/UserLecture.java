package com.mzcteam01.mzcproject01be.domains.user.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
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
@Table(name="user_lecture")
public class UserLecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 강의 ID: OnlineLecture 또는 OfflineLecture PK
    @Column(name = "lecture_id", nullable = false)
    private int lectureId;

    // 온라인 강의 여부
    // 0 (오프라인), 1(온라인)
    @Column(name = "is_online", nullable = false)
    private int isOnline;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public void update( LocalDateTime expiredAt ) {
        this.expiredAt = expiredAt;
    }
}

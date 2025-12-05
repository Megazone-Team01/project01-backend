package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import com.mzcteam01.mzcproject01be.domains.day.entity.Day;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "offline_lecture")
public class OfflineLecture extends Lecture {

    @Column(name = "max_num", nullable = false)
    @Min(value = 1)
    private int maxNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "start_time", nullable = false, length = 4)
    private String startTimeAt;

    @Column(name = "end_time", nullable = false, length = 4)
    private String endTimeAt;

    @Column(name = "day", nullable = false)
    private int day;
}

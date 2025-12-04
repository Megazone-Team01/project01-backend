package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import com.mzcteam01.mzcproject01be.domains.day.entity.Day;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "offline_lecture")
public class OfflineLecture extends Lecture {

    @Column(name = "max_num")
    @Min(value = 1)
    private int maxNum;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "room_id")
//    private Room room;

    @Column(name = "start_time", length = 4)
    private String startTimeAt;

    @Column(name = "end_time", length = 4)
    private String endTimeAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_value")
    private Day day;
}

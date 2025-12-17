package com.mzcteam01.mzcproject01be.domains.lecture.entity;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
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
@ToString
public class OfflineLecture extends Lecture {

    @Column(name = "max_num", nullable = false)
    @Min(value = 1)
    private int maxNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "start_time", nullable = false, length = 4)
    private String startTimeAt;

    @Column(name = "end_time", nullable = false, length = 4)
    private String endTimeAt;

    @Column(name = "day", nullable = false)
    private int day;

    public void update(String name, User teacher, Integer price, LocalDateTime startAt, LocalDateTime endAt, String description, Integer maxNum, Room room,
                       String startTimeAt, String endTimeAt, Integer day) {
        super.update( name, teacher, price, startAt, endAt, description );
        if( maxNum != null ) this.maxNum = maxNum;
        if( room != null ) this.room = room;
        if( startTimeAt != null ) this.startTimeAt = startTimeAt;
        if( endTimeAt != null ) this.endTimeAt = endTimeAt;
        if( day != null ) this.day = day;
    }
}
package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "offline_meeting")
public class OfflineMeeting extends Meeting{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public void update(String name, LocalDateTime startAt, LocalDateTime endAt, Room room) {
        super.update(name, startAt, endAt);
        if( room != null ) this.room = room;
    }
}

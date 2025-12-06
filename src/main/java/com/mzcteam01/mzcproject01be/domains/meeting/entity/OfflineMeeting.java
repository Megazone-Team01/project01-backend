package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "offline_meeting")
public class OfflineMeeting extends Meeting{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

}

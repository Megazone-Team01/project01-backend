package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Builder
@Table(name = "offline_meeting")
public class OfflineMeeting extends Meeting{

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "room_id")
//    private Room room;

}

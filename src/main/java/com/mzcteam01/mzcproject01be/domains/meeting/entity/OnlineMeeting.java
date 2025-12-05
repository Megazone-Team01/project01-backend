package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "online_meeting")
public class OnlineMeeting extends Meeting{

    @Column(name = "location", nullable = false, length = 100)
    private String location;
}

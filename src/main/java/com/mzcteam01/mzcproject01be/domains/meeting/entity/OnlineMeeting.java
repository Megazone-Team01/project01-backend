package com.mzcteam01.mzcproject01be.domains.meeting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "online_meeting")
public class OnlineMeeting extends Meeting{

    @Column(name = "location", length = 100)
    private String location;
}

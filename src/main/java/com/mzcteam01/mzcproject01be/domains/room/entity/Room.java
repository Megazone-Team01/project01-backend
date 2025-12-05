package com.mzcteam01.mzcproject01be.domains.room.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "room" )
public class Room extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "name", length = 20 )
    private String name;

    //@ManyToOne( fetch = FetchType.LAZY )
    //@JoinColumn( name = "organization_id" )
    //private Organization organization;

    @Column( name = "location" )
    private String location;

    @Column( name = "max_num" )
    @Min( value = 1 )
    private int maxNum;

    @Column( name = "start_at" )
    private LocalDateTime startAt;

    @Column( name = "end_at" )
    private LocalDateTime endAt;

    // @ManyToOne( fetch = FetchType.LAZY )
    // @JoinColumn( name = "manager_id" )
    //private User manager;

    @Enumerated( EnumType.STRING )
    @Column( name = "status", length = 50 )
    private RoomStatus status;
}

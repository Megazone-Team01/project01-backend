package com.mzcteam01.mzcproject01be.domains.room.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
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

    @Column( name = "name", length = 20, nullable = false )
    private String name;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "organization_id", nullable = false )
    private Organization organization;

    @Column( name = "location", nullable = false )
    private String location;

    @Column( name = "max_num", nullable = false )
    @Min( value = 1 )
    private int maxNum;

    @Column( name = "start_at", nullable = false )
    private LocalDateTime startAt;

    @Column( name = "end_at", nullable = false )
    private LocalDateTime endAt;

     @ManyToOne( fetch = FetchType.LAZY )
     @JoinColumn( name = "manager_id", nullable = false )
    private User manager;

    @Enumerated( EnumType.STRING )
    @Column( name = "status", length = 50, nullable = false )
    private RoomStatus status;

    public void update( String name, String location, Integer maxNum, LocalDateTime startAt, LocalDateTime endAt, User manager, RoomStatus status ) {
        if( name != null ) this.name = name;
        if( location != null ) this.location = location;
        if( maxNum != null ) this.maxNum = maxNum;
        if( startAt != null ) this.startAt = startAt;
        if( endAt != null ) this.endAt = endAt;
        if( manager != null ) this.manager = manager;
        if( status != null ) this.status = status;
    }
}

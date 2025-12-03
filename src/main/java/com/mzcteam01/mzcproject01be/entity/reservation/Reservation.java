package com.mzcteam01.mzcproject01be.entity.reservation;

import com.mzcteam01.mzcproject01be.entity.BaseEntity;
import com.mzcteam01.mzcproject01be.entity.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "reservation" )
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    // @ManyToOne( fetch = FetchType.EAGER )
    // @JoinColumn( name = "user_id" )
    // private User user;

     @ManyToOne( fetch = FetchType.LAZY )
     @JoinColumn( name = "room_id" )
     private Room room;

     @Column( name = "start_at" )
     private LocalDateTime startAt;

     @Column( name = "end_at" )
    private LocalDateTime endAt;
}

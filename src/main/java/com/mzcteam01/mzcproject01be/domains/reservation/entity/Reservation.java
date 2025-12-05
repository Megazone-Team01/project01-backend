package com.mzcteam01.mzcproject01be.domains.reservation.entity;

import com.mzcteam01.mzcproject01be.common.base.BaseEntity;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
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

    // EAGER 사유: Reservation을 조회할 때 필수적으로 사용자 정보를 함께 조회함
     @ManyToOne( fetch = FetchType.EAGER )
     @JoinColumn( name = "user_id", nullable = false )
     private User user;

     @ManyToOne( fetch = FetchType.LAZY )
     @JoinColumn( name = "room_id", nullable = false )
     private Room room;

     @Column( name = "start_at", nullable = false )
     private LocalDateTime startAt;

     @Column( name = "end_at", nullable = false )
    private LocalDateTime endAt;
}

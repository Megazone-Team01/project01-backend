package com.mzcteam01.mzcproject01be.domains.reservation.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.reservation.dto.response.AdminGetReservationResponse;
import com.mzcteam01.mzcproject01be.domains.reservation.entity.Reservation;
import com.mzcteam01.mzcproject01be.domains.reservation.repository.ReservationRepository;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.AdminGetRoomResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public void create(int userId, int roomId, LocalDateTime startAt, LocalDateTime endAt) {
        User user = userRepository.findById( userId ).orElseThrow( () -> new CustomException("해당하는 사용자가 존재하지 않습니다") );
        Room room = roomRepository.findById( roomId ).orElseThrow( () -> new CustomException("해당하는 스터디룸이 존재하지 않습니다" ) );
        Reservation reservation = Reservation.builder()
                .user( user )
                .room( room )
                .startAt( startAt )
                .endAt( endAt )
                .build();
        reservationRepository.save( reservation );
    }

    // 스터디룸 예약은 업데이트 불가f
    @Transactional
    public void delete( int id, int deletedBy ){
        Reservation reservation = reservationRepository.findById( id ).orElseThrow( () -> new CustomException("존재하지 않는 예약입니다") );
        reservation.delete( deletedBy );
    }

    public AdminGetReservationResponse findById( int id ){
        return AdminGetReservationResponse.of( reservationRepository.findById( id )
                .orElseThrow( () -> new CustomException("해당하는 예약이 존재하지 않습니다")));
    }

    public List<AdminGetReservationResponse> findAll(){
        return reservationRepository.findAll().stream().map(AdminGetReservationResponse::of).toList();
    }
}

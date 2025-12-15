package com.mzcteam01.mzcproject01be.domains.room.service;

import com.mzcteam01.mzcproject01be.common.exception.RoomErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomDetailResponse;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.entity.RoomStatus;
import com.mzcteam01.mzcproject01be.domains.room.repository.QRoomRepository;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final QRoomRepository qRoomRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public void create(
            String name,
            int organizationId,
            String location,
            int maxNum,
            LocalDateTime startAt,
            LocalDateTime endAt,
            int managerId,
            RoomStatus status
    ){
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new CustomException("해당하는 아카데미가 존재하지 않습니다")
        );
        User manager = userRepository.findById( managerId).orElseThrow(
                () -> new CustomException("해당하는 사용자의 정보가 존재하지 않습니다")
        );
        Room room = Room.builder()
                .name( name )
                .organization( organization )
                .location( location )
                .maxNum( maxNum )
                .startAt( startAt )
                .endAt( endAt )
                .manager( manager )
                .status( status )
                .build();
        roomRepository.save( room );
    }

    @Transactional
    public void update(
            int roomId,
            String name,
            String location,
            Integer maxNum,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer managerId,
            RoomStatus status
    ){
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException("해당하는 스터디룸을 찾을 수 없습니다")
        );

        User manager = null;
        if(managerId != null){
            manager = userRepository.findById( managerId).orElseThrow(
                    () -> new CustomException("해당하는 사용자가 없습니다")
            );
        }
        room.update( name, location, maxNum, startAt, endAt, manager, status );
    }

    public void delete( int roomId, int deletedBy ){
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException("해당하는 스터디룸을 찾을 수 없습니다")
        );
        room.delete( deletedBy );
    }

    public List<RoomListResponse> getAvailableRooms(int organizationId) {

        List<Room> rooms = qRoomRepository.findByOrganizationIdAndStatus(organizationId, RoomStatus.AVAILABLE);

        return rooms.stream()
                .map(RoomListResponse::from)
                .collect(Collectors.toList());
    }

    public RoomDetailResponse getRoomDetails(int roomId) {

        Room room = qRoomRepository.findByIdWithDetails(roomId)
                .orElseThrow(()-> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()));
        return RoomDetailResponse.from(room);
    }
}

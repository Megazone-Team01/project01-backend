package com.mzcteam01.mzcproject01be.domains.room.service;

import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomDetailResponse;
import com.mzcteam01.mzcproject01be.domains.room.dto.response.RoomListResponse;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;

import java.util.List;

public interface RoomService {

    // 회의실 생성
    // 회의실 정보 수정
    // 희의실 삭제

    // 특정 기관의 사용 가능한 회의실 목록 조회
    List<RoomListResponse> getAvailableRooms(int organizationId);

    // 회의실 상세 정보 조회
    RoomDetailResponse getRoomDetails(int roomId);
}

package com.mzcteam01.mzcproject01be.domains.meeting.service;

import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOfflineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.dto.response.AdminGetOnlineMeetingResponse;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.Meeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OfflineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.meeting.repository.OnlineMeetingRepository;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OnlineMeetingRepository onlineMeetingRepository;
    private final OfflineMeetingRepository offlineMeetingRepository;
    private final RoomRepository roomRepository;

    public void create(boolean isOnline, String name, int organizationId, int teacherId, int studentId, LocalDateTime startAt, LocalDateTime endAt,
                       String location, Integer roomId ){
        User teacher = userRepository.findById( teacherId ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()) );
        User student = userRepository.findById( studentId ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()) );
        Organization organization = organizationRepository.findById( organizationId ).orElseThrow( () -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()) );

        if( isOnline ){
            OnlineMeeting meeting = OnlineMeeting.builder()
                    .name( name )
                    .organization( organization )
                    .teacher( teacher )
                    .student( student )
                    .startAt( startAt )
                    .endAt( endAt )
                    .status(0)
                    .location( location )
                    .build();
            onlineMeetingRepository.save(meeting);
        }
        else {
            Room room = roomRepository.findById( roomId ).orElseThrow( () -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()) );
            OfflineMeeting meeting = OfflineMeeting.builder()
                    .name( name )
                    .organization( organization )
                    .teacher( teacher )
                    .student( student )
                    .startAt( startAt )
                    .endAt( endAt )
                    .status( 0 )
                    .room( room )
                    .build();
            offlineMeetingRepository.save(meeting);
        }
    }

    public void updateOnlineMeeting( int id, String name, LocalDateTime startAt, LocalDateTime endAt, String location ){
        OnlineMeeting meeting = onlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()) );
        meeting.update( name, startAt, endAt, location );
    }

    public void updateOfflineMeeting( int id, String name, LocalDateTime startAt, LocalDateTime endAt, Integer roomId ){
        OfflineMeeting meeting = offlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage() ) );
        Room room = null;
        if( roomId != null ){
            room = roomRepository.findById( roomId ).orElseThrow( () -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()) );
        }
        meeting.update( name, startAt, endAt, room );
    }

    public void delete( boolean isOnline, int id, int deletedBy ){
        Meeting meeting;
        if( isOnline ) meeting = onlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()) );
        else meeting = offlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()) );
        meeting.delete( deletedBy );
    }

    public List<AdminGetMeetingResponse> findAll(){
        List<AdminGetMeetingResponse> result = new ArrayList<>();
        onlineMeetingRepository.findAll().forEach(meeting -> {
            result.add(AdminGetMeetingResponse.of( meeting, true, meeting.getLocation() ));
        });
        offlineMeetingRepository.findAll().forEach(meeting -> {
            result.add(AdminGetMeetingResponse.of( meeting, false, meeting.getRoom().getName() ));
        });
        return result;
    }

    public AdminGetOnlineMeetingResponse findOnlineMeetingById(int id ){
        return AdminGetOnlineMeetingResponse.of( onlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()) ) );
    }

    public AdminGetOfflineMeetingResponse findOfflineMeetingById(int id ){
        return AdminGetOfflineMeetingResponse.of( offlineMeetingRepository.findById( id ).orElseThrow( () -> new CustomException(MeetingErrorCode.MEETING_NOT_FOUND.getMessage()) ) );
    }
}

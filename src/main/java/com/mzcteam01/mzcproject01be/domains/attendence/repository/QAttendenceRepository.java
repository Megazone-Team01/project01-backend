package com.mzcteam01.mzcproject01be.domains.attendence.repository;

import com.mzcteam01.mzcproject01be.domains.attendence.entity.Attendance;
import com.mzcteam01.mzcproject01be.domains.attendence.entity.QAttendance;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class QAttendenceRepository {
    private final JPAQueryFactory query;

    public List<Attendance> list(
            String searchString,
            LocalDateTime checkIn,
            LocalDateTime checkOut
    ){
        QAttendance attendance = QAttendance.attendance;

        BooleanBuilder where = new BooleanBuilder();

        where.and( attendance.deletedAt.isNull() );

        if( searchString != null ){
            // 추가 수정 필요
        }

        if( checkIn != null ) where.and( attendance.checkIn.after( checkIn ) );

        if( checkOut != null ) where.and( attendance.checkOut.before( checkOut ) );

        List<Attendance> results = query
                .select( attendance )
                .from( attendance )
                .where( where )
                .fetch();

        return results;
    }
}

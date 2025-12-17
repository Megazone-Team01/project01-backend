package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.QOfflineMeeting;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QOfflineMeetingRepository {
    private final JPAQueryFactory query;

    public List<OfflineMeeting> list(
            String searchString,
            LocalDateTime start,
            LocalDateTime end,
            Integer status
    ){
        QOfflineMeeting offlineMeeting = QOfflineMeeting.offlineMeeting;

        BooleanBuilder where = new BooleanBuilder();
        where.and( offlineMeeting.deletedAt.isNull() );
        if( searchString != null ){
            // 글이 어디에 포함되는걸 제한할지 결정 필요
        }
        if( start != null ) where.and( offlineMeeting.startAt.after( start ) );
        if( end != null ) where.and( offlineMeeting.endAt.before( end ) );
        if( status != null ) where.and( offlineMeeting.status.eq( status ) );
        List<OfflineMeeting> results = query
                .select( offlineMeeting )
                .from( offlineMeeting )
                .where( where )
                .fetch();
        return results;
    }

    public List<OfflineMeeting> findByStudentId(int studentId, Integer status) {
        QOfflineMeeting offlineMeeting = QOfflineMeeting.offlineMeeting;

        BooleanBuilder where = new BooleanBuilder();
        where.and(offlineMeeting.deletedAt.isNull());
        where.and(offlineMeeting.student.id.eq(studentId));

        if (status != null) {
            where.and(offlineMeeting.status.eq(status));
        }

        return query
                .selectFrom(offlineMeeting)
                .join(offlineMeeting.teacher).fetchJoin()
                .leftJoin(offlineMeeting.room).fetchJoin()
                .where(where)
                .orderBy(offlineMeeting.startAt.desc())
                .fetch();
    }
}

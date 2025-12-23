package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OfflineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.QOfflineMeeting;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.meeting.entity.QOfflineMeeting.offlineMeeting;

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


    /**
     * 선생님 ID와 날짜 범위로 오프라인 미팅 조회
     */
    public List<OfflineMeeting> findByTeacherIdAndDateRange(int teacherId, LocalDateTime startAt, LocalDateTime endAt) {
        return query
                .selectFrom(offlineMeeting)
                .where(
                        offlineMeeting.teacher.id.eq(teacherId),
                        offlineMeeting.startAt.between(startAt, endAt),
                        offlineMeeting.deletedAt.isNull()
                )
                .fetch();
    }

    /**
     * 선생님의 특정 시간대에 이미 예약이 있는지 확인 (중복 예약 방지)
     */
    public boolean existsByTeacherIdAndTimeRange(int teacherId, LocalDateTime startAt, LocalDateTime endAt) {
        Long count = query
                .select(offlineMeeting.count())
                .from(offlineMeeting)
                .where(
                        offlineMeeting.teacher.id.eq(teacherId),
                        offlineMeeting.deletedAt.isNull(),
                        offlineMeeting.status.goe(0),
                        offlineMeeting.startAt.lt(endAt),
                        offlineMeeting.endAt.gt(startAt)
                )
                .fetchOne();

        return count != null && count > 0;
    }

    public List<OfflineMeeting> findByTeacherId(int teacherId, Integer status) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(offlineMeeting.teacher.id.eq(teacherId));
        builder.and(offlineMeeting.deletedAt.isNull());

        if (status != null) {
            builder.and(offlineMeeting.status.eq(status));
        }

        return query
                .selectFrom(offlineMeeting)
                .where(builder)
                .orderBy(offlineMeeting.startAt.desc())
                .fetch();
    }
}

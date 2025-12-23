package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.QOnlineMeeting;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.mzcteam01.mzcproject01be.domains.meeting.entity.QOnlineMeeting.onlineMeeting;

@RequiredArgsConstructor
@Repository
public class QOnlineMeetingRepository {
    private final JPAQueryFactory query;

    public List<OnlineMeeting> list(
            String searchString,
            LocalDateTime start,
            LocalDateTime end,
            Integer status
    ){
        QOnlineMeeting onlineMeeting = QOnlineMeeting.onlineMeeting;

        BooleanBuilder where = new BooleanBuilder();
        where.and( onlineMeeting.deletedAt.isNull() );
        if( searchString != null ){
            // 글이 어디에 포함되는걸 제한할지 결정 필요
        }
        if( start != null ) where.and( onlineMeeting.startAt.after( start ) );
        if( end != null ) where.and( onlineMeeting.endAt.before( end ) );
        if( status != null ) where.and( onlineMeeting.status.eq( status ) );
        List<OnlineMeeting> results = query
                .select( onlineMeeting )
                .from( onlineMeeting )
                .where( where )
                .fetch();
        return results;
    }

    public List<OnlineMeeting> findByStudentId(int studentId, Integer status) {
        QOnlineMeeting onlineMeeting = QOnlineMeeting.onlineMeeting;

        BooleanBuilder where = new BooleanBuilder();
        where.and(onlineMeeting.deletedAt.isNull());
        where.and(onlineMeeting.student.id.eq(studentId));

        if (status != null) {
            where.and(onlineMeeting.status.eq(status));
        }

        return query
                .selectFrom(onlineMeeting)
                .join(onlineMeeting.teacher).fetchJoin()
                .where(where)
                .orderBy(onlineMeeting.startAt.desc())
                .fetch();
    }


    /**
     * 선생님 ID와 날짜 범위로 온라인 미팅 조회
     */
    public List<OnlineMeeting> findByTeacherIdAndDateRange(int teacherId, LocalDateTime startAt, LocalDateTime endAt) {
        return query
                .selectFrom(onlineMeeting)
                .where(
                        onlineMeeting.teacher.id.eq(teacherId),
                        onlineMeeting.startAt.between(startAt, endAt),
                        onlineMeeting.deletedAt.isNull()
                )
                .fetch();
    }

    /**
     * 선생님의 특정 시간대에 이미 예약이 있는지 확인 (중복 예약 방지)
     */
    public boolean existsByTeacherIdAndTimeRange(int teacherId, LocalDateTime startAt, LocalDateTime endAt) {
        Long count = query
                .select(onlineMeeting.count())
                .from(onlineMeeting)
                .where(
                        onlineMeeting.teacher.id.eq(teacherId),
                        onlineMeeting.deletedAt.isNull(),
                        onlineMeeting.status.goe(0), // 대기(0) 또는 승인(1) 상태
                        // 시간 겹침 체크
                        onlineMeeting.startAt.lt(endAt),
                        onlineMeeting.endAt.gt(startAt)
                )
                .fetchOne();

        return count != null && count > 0;
    }

    public List<OnlineMeeting> findByTeacherId(int teacherId, Integer status) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(onlineMeeting.teacher.id.eq(teacherId));
        builder.and(onlineMeeting.deletedAt.isNull());

        if (status != null) {
            builder.and(onlineMeeting.status.eq(status));
        }

        return query
                .selectFrom(onlineMeeting)
                .where(builder)
                .orderBy(onlineMeeting.startAt.desc())
                .fetch();
    }
}

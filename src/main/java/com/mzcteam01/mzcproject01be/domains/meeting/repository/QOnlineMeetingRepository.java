package com.mzcteam01.mzcproject01be.domains.meeting.repository;

import com.mzcteam01.mzcproject01be.domains.meeting.entity.OnlineMeeting;
import com.mzcteam01.mzcproject01be.domains.meeting.entity.QOnlineMeeting;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
}

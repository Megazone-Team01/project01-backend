package com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl;

import com.mzcteam01.mzcproject01be.domains.file.entity.QFile;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OnlineLecture;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUser;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUserLecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mzcteam01.mzcproject01be.domains.lecture.entity.QOnlineLecture.onlineLecture;

@Slf4j
@RequiredArgsConstructor
@Repository
public class QOnlineLectureRepository {
    private final JPAQueryFactory queryFactory;
    QOrganization organization = QOrganization.organization;
    QUser user = QUser.user;
    QUserLecture userLecture = QUserLecture.userLecture;
    QFile file = QFile.file;

    public Optional<OnlineLecture> findById(int id){
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(onlineLecture)
                        .join(onlineLecture.thumbnailFile, file).fetchJoin()
                        .join(onlineLecture.teacher, user).fetchJoin()
                        .join(onlineLecture.organization, organization).fetchJoin()
                        .where(
                                onlineLecture.id.eq(id)
                        )
                        .fetchOne()
        );
    }


    public Page<OnlineLecture> findOnlineLectures(Integer searchType, Pageable pageable, String keyword) {
        log.info("findOnlineLectures start: " + keyword);

        List<OnlineLecture> online;

        if (searchType != null && searchType == 3) {
            // 인기순: Join + GroupBy 사용
            online = queryFactory
                    .select(onlineLecture)
                    .from(onlineLecture)
                    .join(onlineLecture.thumbnailFile, file).fetchJoin()
                    .leftJoin(userLecture)
                    .on(userLecture.lectureId.eq(onlineLecture.id).and(userLecture.isOnline.eq(1)))
                    .where(keywordContains(keyword))
                    .groupBy(onlineLecture.id)
                    .orderBy(userLecture.count().desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {
            // 날짜순 (최신순/오래된순)
            online = queryFactory
                    .selectFrom(onlineLecture)
                    .where(keywordContains(keyword))
                    .orderBy(getCreatedOrder(searchType, onlineLecture.startAt))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        Long total = queryFactory
                .select(onlineLecture.count())
                .from(onlineLecture)
                .where(keywordContains(keyword))
                .fetchOne();

        return new PageImpl<>(online, pageable, total == null ? 0L : total);
    }

    public List<String> findOnlineLectureNamesByTeacherId(int teacherId) {
        return queryFactory
                .select(onlineLecture.name)
                .from(onlineLecture)
                .where(onlineLecture.teacher.id.eq(teacherId))
                .fetch();
    }

    private OrderSpecifier<?> getCreatedOrder(
            Integer searchType,
            ComparableExpressionBase<?> startAt
    ) {
        return (searchType == null || searchType == 1) ?
                    startAt.asc()
                    :startAt.desc();

    }

    private BooleanExpression keywordContains(String keyword){
        if(keyword == null || keyword.trim().isEmpty()){
            return null;
        }
        return onlineLecture.name.containsIgnoreCase(keyword);
    }


}

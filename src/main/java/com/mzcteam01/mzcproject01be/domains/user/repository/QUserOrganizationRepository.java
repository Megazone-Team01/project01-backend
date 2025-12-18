package com.mzcteam01.mzcproject01be.domains.user.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.QUserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class QUserOrganizationRepository {
    private final JPAQueryFactory query;

    public List<UserOrganization> findActiveByUserAndOwner(int userId) {
        QUserOrganization qUserOrganization = QUserOrganization.userOrganization;
        QOrganization qOrganization = QOrganization.organization;

        return query
                .select(qUserOrganization)
                .from(qUserOrganization)
                .join(qUserOrganization.organization, qOrganization)
                .where(qOrganization.owner.id.eq(userId),
                        qUserOrganization.status.eq(0),
                        qUserOrganization.deletedAt.isNull()
                )
                .fetch();
    }


    public List<UserOrganization> list(
            String searchString,
            LocalDateTime registered,
            LocalDateTime expired
    ){
        QUserOrganization userOrganization = QUserOrganization.userOrganization;

        BooleanBuilder where = new BooleanBuilder();

        where.and( userOrganization.deletedAt.isNull() );

        if( searchString != null ){
            // 추가 수정 필요
        }

        if( registered != null ) where.and( userOrganization.registeredAt.after( registered ) );

        if( expired != null ) where.and( userOrganization.expiredAt.before( expired ) );

        List<UserOrganization> results = query
                .select( userOrganization )
                .from( userOrganization )
                .where( where )
                .fetch();

        return results;
    }
}

package com.mzcteam01.mzcproject01be.domains.organization.repository;

import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.entity.QOrganization;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QOrganizationRepository {
    private final JPAQueryFactory factory;

    public List<Organization> list(
            String searchString,
            Integer status,
            Integer isOnline,
            String ownerName,
            Integer ownerId
    ){
        QOrganization organization = QOrganization.organization;

        BooleanBuilder where = new BooleanBuilder();
        if( searchString != null ) where.and( organization.name.contains( searchString ) );

        if( status != null ) where.and( organization.status.eq(status) );
        if( isOnline != null ) where.and( organization.isOnline.eq(isOnline) );
        if( ownerName != null ) where.and( organization.owner.name.eq(ownerName) );
        if( ownerId != null ) where.and( organization.owner.id.eq(ownerId) );

        return factory.
                select( organization )
                .from( organization )
                .where( where )
                .fetch();
    }
}
